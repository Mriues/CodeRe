<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>角色管理</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
      <el-row>
        <el-col>
          <el-button type="primary"
                     @click="addDialogVisble = true">添加角色</el-button>
        </el-col>
      </el-row>

      <el-table :data="roleList"
                border
                stripe>
        <el-table-column type="expand">
          <template slot-scope="scope">
            <el-row :class="['bdbottom', i1 === 0 ? 'bdtop' : '', 'vcenter']"
                    v-for="(item1, i1) in scope.row.children"
                    :key="item1.id">
              <!-- 渲染一级权限 占5列-->
              <el-col :span="5">
                <el-tag closable
                        @close="removeRightById(scope.row,item1.id)">{{item1.authName}}</el-tag>
                <i class="el-icon-caret-right"></i>
              </el-col>
              <!-- 渲染二级和二级权限 -->
              <el-col :span="19">
                <el-row :class="[i2 === 0 ? '' : 'bdtop', 'vcenter']"
                        v-for="(item2, i2) in item1.children"
                        :key="item2.id">
                  <!-- 二级渲染 -->
                  <el-col :span="6">
                    <el-tag type="success"
                            closable
                            @close="removeRightById(scope.row,item2.id)">{{item2.authName}}</el-tag>
                    <i class="el-icon-caret-right"></i>
                  </el-col>
                  <!-- 三级渲染 -->
                  <el-col :span="18">
                    <el-tag type="warning"
                            v-for="item3 in item2.children"
                            :key="item3.id"
                            closable
                            @close="removeRightById(scope.row,item3.id)">{{item3.authName}}</el-tag>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </template>
        </el-table-column>

        <el-table-column type="index"
                         label="#"></el-table-column>
        <el-table-column label="角色名称"
                         prop="roleName"></el-table-column>
        <el-table-column label="角色描述"
                         prop="roleDesc"></el-table-column>
        <el-table-column label="操作"
                         width="300px">
          <template slot-scope="scope">
            <el-tooltip effect="dark"
                        content="编辑"
                        placement="top"
                        :enterable="false">
              <el-button type="primary"
                         icon="el-icon-edit"
                         size="mini"
                         @click="showEditDialog(scope.row.id)"></el-button>
            </el-tooltip>
            <el-tooltip effect="dark"
                        content="删除"
                        placement="top"
                        :enterable="false">
              <el-button type="danger"
                         icon="el-icon-delete"
                         size="mini"
                         @click="removeRoleById(scope.row.id)"></el-button>
            </el-tooltip>
            <el-tooltip effect="dark"
                        content="分配权限"
                        placement="top"
                        :enterable="false">
              <el-button type="warning"
                         icon="el-icon-setting"
                         size="mini"
                         @click="showSetRightDialog(scope.row)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 分配权限对话框 -->
    <el-dialog title="分配权限"
               :visible.sync="setRightDialogVisible"
               width="50%">
      <!-- 树形控件 -->
      <el-tree :data="rightsList"
               :props="treeProps"
               show-checkbox
               node-key="id"
               default-expand-all
               :default-checked-keys="defKeys"
               ref="treeRef"></el-tree>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="setRightDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="allotRights">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 添加角色对话框 -->
    <el-dialog title="添加角色"
               :visible.sync="addDialogVisble"
               width="50%"
               @close="addDialogClosed">
      <el-form :model="addForm"
               :rules="FormRules"
               ref="addFormRef"
               label-width="100px">
        <el-form-item label="角色名称"
                      prop="rolename">
          <el-input v-model="addForm.rolename"></el-input>
        </el-form-item>
        <el-form-item label="角色描述"
                      prop="roledesc">
          <el-input v-model="addForm.roledesc"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="addDialogVisble = false">取 消</el-button>
        <el-button type="primary"
                   @click="addRole">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑角色对话框 -->
    <el-dialog title="编辑角色"
               :visible.sync="editDialogVisble"
               width="50%"
               @close="editDialogClosed">
      <el-form :model="editForm"
               :rules="FormRules"
               ref="editFormRef"
               label-width="100px">
        <el-form-item label="角色名称"
                      prop="rolename">
          <el-input v-model="editForm.rolename"></el-input>
        </el-form-item>
        <el-form-item label="角色描述"
                      prop="roledesc">
          <el-input v-model="editForm.roledesc"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="editDialogVisble = false">取 消</el-button>
        <el-button type="primary"
                   @click="editRole">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      roleList: [],

      // 控制分配权限对话框默认值
      setRightDialogVisible: false,
      rightsList: [],
      treeProps: {
        label: 'authName',
        children: 'children'
      },
      defKeys: [],
      // 当前要分配角色权限的id
      roleId: '',
      addDialogVisble: false,
      editDialogVisble: false,
      addForm: {
        rolename: '',
        roledesc: ''
      },
      editForm: {
        roleid: '',
        rolename: '',
        roledesc: ''
      },
      FormRules: {
        rolename: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ],
        roledesc: [
          { required: true, message: '请输入角色描述', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getRoleList()
  },
  methods: {
    async getRoleList () {
      const { data: res } = await this.$http.get('roles')
      if (res.meta.status !== 200) {
        return this.$message.error('获取角色列表失败')
      }
      this.roleList = res.data
    },
    async removeRightById (role, rightId) {
      const confirmResult = await this.$confirm('此操作将删除该权限, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)

      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }

      const { data: res } = await this.$http.delete(`roles/${role.id}/rights/${rightId}`)

      if (res.meta.status !== 204) {
        return this.$message.error('删除权限失败')
      }

      // 不调用列表方法避免页面刷新
      role.children = res.data
      return this.$message.success('已删除该权限')
    },
    async showSetRightDialog (role) {
      // 清除已经存储的权限
      this.defKeys = []

      this.roleId = role.id
      const { data: res } = await this.$http.get('rights/tree')
      if (res.meta.status !== 200) {
        return this.$message.error('获取权限数据失败！')
      }

      this.rightsList = res.data

      // 递归获取三级节点id
      this.getLeafKeys(role, this.defKeys)

      this.setRightDialogVisible = true
    },
    // 递归获取角色所有三级权限id
    getLeafKeys (node, arr) {
      // 当前节点不包含children则是三级权限
      if (!node.children) {
        return arr.push(node.id)
      }

      node.children.forEach(item => this.getLeafKeys(item, arr))
    },
    async allotRights () {
      const keys = [
        ...this.$refs.treeRef.getCheckedKeys(),
        ...this.$refs.treeRef.getHalfCheckedKeys()
      ]

      const idStr = keys.join(',')

      const { data: res } = await this.$http.post(`roles/${this.roleId}/rights`, { rids: idStr })

      if (res.meta.status !== 201) {
        return this.$$message.error('分配权限失败！')
      }

      this.$message.success('分配权限成功！')
      this.getRoleList()
      this.setRightDialogVisible = false
    },
    addDialogClosed () {
      this.$refs.addFormRef.resetFields()
      this.addDialogVisble = false
    },
    async addRole () {
      const { data: res } = await this.$http.post('roles', this.addForm)
      if (res.meta.status !== 201) {
        return this.$message.error('添加角色失败！')
      }

      this.getRoleList()
      this.$message.success('添加角色成功！')
      this.addDialogVisble = false
    },
    async showEditDialog (id) {
      const { data: res } = await this.$http.get(`roles/${id}`)
      if (res.meta.status !== 200) {
        return this.$message.error('获取角色信息失败！')
      }

      this.editForm.roleid = res.data.roleId
      this.editForm.rolename = res.data.roleName
      this.editForm.roledesc = res.data.roleDesc
      this.editDialogVisble = true
    },
    editDialogClosed () {
      this.$refs.editFormRef.resetFields()
      this.editDialogVisble = false
    },
    async editRole () {
      const { data: res } = await this.$http.put(`roles/${this.editForm.roleid}`, this.editForm)
      if (res.meta.status !== 201) {
        return this.$message.error('更新角色信息失败!')
      }

      this.getRoleList()
      this.$message.success('更新角色信息成功！')
      this.editDialogVisble = false
    },
    async removeRoleById (id) {
      const confirmRes = await this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)

      if (confirmRes !== 'confirm') {
        return this.$message.info('已取消删除')
      }

      const { data: res } = await this.$http.delete(`roles/${id}`)
      if (res.meta.status !== 204) {
        return this.$message.error('删除失败！')
      }

      this.getRoleList()

      return this.$message.success('删除角色成功！')
    }
  }
}
</script>

<style lang="less" scoped>
.el-tag {
  margin: 10px;
}

.bdtop {
  border-top: 1px solid #eee;
}

.bdbottom {
  border-bottom: 1px solid #eee;
}

.vcenter {
  display: flex;
  align-items: center;
}
</style>

<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="请输入内容"
                    v-model="queryInfo.query"
                    clearable
                    @clear="getGoodsList()">
            <el-button slot="append"
                       icon="el-icon-search"
                       @click="getGoodsList()">
            </el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary"
                     @click="goodsAddPage">添加商品</el-button>
        </el-col>
      </el-row>

      <!-- 表格区域 -->
      <el-table :data="goodsList"
                border
                stripe>
        <el-table-column type="index"
                         label="#">
        </el-table-column>
        <el-table-column label="商品名称"
                         prop="goods_name">
        </el-table-column>
        <el-table-column label="商品价格（元）"
                         width="95px"
                         prop="goods_price">
        </el-table-column>
        <el-table-column label="商品重量"
                         width="70px"
                         prop="goods_weight">
        </el-table-column>
        <el-table-column label="商品创建时间"
                         width="140px"
                         prop="add_time">
          <template slot-scope="scope">
            <!-- 调用过滤器 -->
            {{scope.row.add_time | dataFormat}}
          </template>
        </el-table-column>
        <el-table-column label="操作"
                         width="130px">
          <template slot-scope="scope">
            <el-button type="primary"
                       icon="el-icon-edit"
                       size="mini"
                       @click="showEditDialog(scope.row.goods_id)"></el-button>
            <el-button type="danger"
                       icon="el-icon-delete"
                       size="mini"
                       @click="removeById(scope.row.goods_id)"></el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区 -->
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     :current-page="queryInfo.pagenum"
                     :page-sizes="[5, 10, 15, 20]"
                     :page-size="queryInfo.pagesize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="total"
                     background>
      </el-pagination>
    </el-card>

    <!-- 编辑对话框 -->
    <el-dialog title="编辑商品"
               :visible.sync="editDialogVisible"
               width="70%"
               @close="editDialogClosed">
      <el-form :model="editForm"
               :rules="editFormRules"
               ref="editFormRef"
               label-width="100px">
        <el-form-item label="商品名称"
                      prop="goods_name">
          <el-input v-model="editForm.goods_name"></el-input>
        </el-form-item>
        <el-form-item label="商品价格"
                      prop="goods_price">
          <el-input v-model="editForm.goods_price"
                    type="number"></el-input>
        </el-form-item>
        <el-form-item label="商品重量"
                      prop="goods_weight">
          <el-input v-model="editForm.goods_weight"
                    type="number"></el-input>
        </el-form-item>
        <el-form-item label="商品数量"
                      prop="goods_number">
          <el-input v-model="editForm.goods_number"
                    type="number"></el-input>
        </el-form-item>
        <el-form-item label="商品介绍"
                      prop="goods_introduce">
          <quill-editor v-model="editForm.goods_introduce"></quill-editor>
        </el-form-item>
      </el-form>
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary"
                   @click="updateGoods()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      queryInfo: {
        query: '',
        pagenum: 1,
        pagesize: 10
      },
      goodsList: [],
      total: 0,
      editDialogVisible: false,
      editForm: {},
      editFormRules: {
        goods_name: [
          { required: true, message: '请输入商品名称', trigger: 'blur' }
        ],
        goods_price: [
          { required: true, message: '请输入商品价格', trigger: 'blur' }
        ],
        goods_weight: [
          { required: true, message: '请输入商品重量', trigger: 'blur' }
        ],
        goods_number: [
          { required: true, message: '请输入商品数量', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getGoodsList()
  },
  methods: {
    // 根据分页获取对应参数列表
    async getGoodsList () {
      const { data: res } = await this.$http.get('goods', { params: this.queryInfo })

      if (res.meta.status !== 200) {
        return this.$message.error('获取失败！')
      }

      this.$message.success('获取商品列表成功！')
      console.log(res.data)
      this.goodsList = res.data.goods
      this.total = res.data.total
    },
    // 触发后得到最新pagesize
    handleSizeChange (newSize) {
      this.queryInfo.pagesize = newSize
      this.getGoodsList()
    },
    handleCurrentChange (newPage) {
      this.queryInfo.pagenum = newPage
      this.getGoodsList()
    },
    async removeById (id) {
      const confirmResult = await this.$confirm('此操作将永久删除该商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)

      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除!')
      }

      const { data: res } = await this.$http.delete(`goods/${id}`)
      if (res.meta.status !== 200) {
        return this.$message.error('删除失败!')
      }

      this.$message.success('已删除该商品!')
      this.getGoodsList()
    },
    async showEditDialog (id) {
      const { data: res } = await this.$http.get(`goods/${id}`)
      if (res.meta.status !== 201) {
        return this.$message.error('获取商品信息失败！')
      }
      console.log(res.data)
      this.editForm = res.data
      this.editDialogVisible = true
    },
    editDialogClosed () {
      this.$refs.editFormRef.resetFields()
    },
    async updateGoods () {
      const { data: res } = await this.$http.put(`goods/${this.editForm.goods_id}`, this.editForm)
      if (res.meta.status !== 200) {
        return this.$message.error('编辑商品信息失败！')
      }
      this.$message.success('编辑商品信息成功！')
      this.getGoodsList()
      this.editDialogVisible = false
    },
    goodsAddPage () {
      this.$router.push('/goods/add')
    }
  }
}
</script>

<style lang="less" scoped>
</style>

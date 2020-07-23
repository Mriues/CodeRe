<template>
  <div class="register_container">
    <div class="register_box">
      <div class="avatar_box">
        <span>注册用户</span>
      </div>

      <el-form ref="registerFormRef"
               :model="registerForm"
               :rules="registerFormRules"
               label-width="0px"
               class="register_form">
        <el-form-item prop="username">
          <el-input v-model="registerForm.username"
                    placeholder="用户名"
                    prefix-icon="el-icon-user-solid"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="registerForm.password"
                    placeholder="用户密码"
                    type="password"
                    prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <el-form-item prop="confirmpassword">
          <el-input v-model="registerForm.confirmpassword"
                    placeholder="再次确认密码"
                    type="password"
                    prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <el-form-item class="btns">
          <el-button type="primary"
                     @click="register"
                     round>注册</el-button>
          <el-button type="info"
                     @click="back"
                     round>返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      registerForm: {
        username: '',
        password: '',
        confirmpassword: ''
      },
      registerFormRules: {
        username: [
          { required: true, message: '请输入注册名字', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入用户密码', trigger: 'blur' },
          { min: 6, message: '密码不能少于6个字符', trigger: 'blur' }
        ],
        confirmpassword: [
          { required: true, message: '请再次确认密码', trigger: 'blur' },
          { min: 6, message: '密码不能少于6个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    register () {
      // eslint-disable-next-line eqeqeq
      if (this.registerForm.password != this.registerForm.confirmpassword) {
        return this.$message.error('两次输入的密码不一致，请重新输入')
      }

      this.$refs.registerFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.post('register', { name: this.registerForm.username, pwd: this.registerForm.password })
        if (!res.success) {
          this.$message.error(res.msg)
          this.$refs.registerFormRef.resetFields()
        }
        if (res.success) {
          this.$message.success(res.msg)
          this.$refs.registerFormRef.resetFields()
          this.$router.go(-1)
        }
      })
    },
    back () {
      this.$router.go(-1)
    }
  }
}
</script>

<style lang="less" scoped>
.register_container {
  background-color: #2b4b6b;
  height: 100%;
}

.register_box {
  width: 450px;
  height: 300px;
  background-color: #fff;
  border-radius: 5px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  .avatar_box {
    height: 20px;
    width: 80px;
    padding: 10px;
    font-weight: 600;
    font-size: 20px;
    position: absolute;
    left: 50%;
    transform: translate(-50%);
  }
}

.register_form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.btns {
  display: flex;
  justify-content: flex-end;
}
</style>

<template>
  <div class="register-container">
    <div class="register-box">
      <div class="col-sm-6">
        <div class="register-card card">
          <div class="card-header">
            <h5>注册</h5>
          </div>
          <div class="card-img">
            <img
              src="../assets/images/sign/sign_4.jpg"
              class="card-img-top"
              alt="..."
            />
          </div>
          <div class="card-body">
            <el-form
              :model="registerForm"
              status-icon
              :rules="rules"
              ref="registerForm"
              label-width="100px"
              class="demo-ruleForm"
            >
              <el-form-item
                label="用户名"
                prop="name"
              >
                <el-input
                  v-model="registerForm.uname"
                  placeholder="请输入用户名"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="密码"
                prop="upassword"
              >
                <el-input
                  type="password"
                  v-model="registerForm.upassword"
                  autocomplete="off"
                  placeholder="请输入密码"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="邮箱"
                prop="uemail"
              >
                <el-input
                  v-model="registerForm.uemail"
                  placeholder="请输入邮箱"
                ></el-input>
              </el-form-item>
              <!-- <el-form-item label="性别" prop="sex">
                <el-input type="text" v-model="registerForm.usex" autocomplete="off" placeholder="请输入性别"></el-input>
              </el-form-item> -->
              <el-form-item label="性别">
                <el-radio-group v-model="registerForm.usex">
                  <el-radio label="男"></el-radio>
                  <el-radio label="女"></el-radio>
                </el-radio-group>
              </el-form-item>
              <!-- <div class="check-code-box">
                <el-form-item
                  label="验证码"
                  prop="checkcode"
                >
                  <el-input
                    type="text"
                    v-model="registerForm.checkcode"
                    autocomplete="off"
                    placeholder="请输入验证码"
                  ></el-input>
                </el-form-item>
                <input
                  type="button"
                  class="code-btn btn btn-secondary"
                  v-model="time"
                  @click="getCheckCode()"
                />
              </div> -->
              <el-form-item>
                <el-button
                  type="primary"
                  @click="login"
                >登录</el-button>
                <el-button @click="register('registerForm')">注册</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      registerForm: {
        uid: 1,
        uemail: '',
        uname: '',
        upassword: '',
        usex: '男',
        uphone: '',
        uheadImg: '',
      },
      rules: {
        uemail: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] },
        ],
        upassword: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        checkcode: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
      },
      time: '获取验证码',
      timer: null,
    }
  },
  methods: {
    // 注册
    register (formName) {
      console.log(this.registerForm)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 调用接口验证
          /*  const data = this.$qs.stringify({
            user: this.registerForm
          }) */
          this.$axios.post('/register', this.registerForm).then((res) => {
            if (res.data.code === 400) {
              this.$message.error('注册失败')
              return
            }
            // 成功 显示注册成功
            this.$message({
              message: '注册成功！正在跳转登录页...',
              type: 'success',
              duration: 800,
              onClose: () => {
                // const loginForm = { email: this.registerForm.uEmail, password: this.registerForm.password }
                this.$router.push({
                  path: '/sign',
                  name: 'Login',
                  // params: {
                  //   loginForm,
                  // },
                })
                // 清除记录
                this.$refs[formName].resetFields()
              },
            })
          })

          // 失败 提示错误信息
          /*  this.$message.error('注册失败！') */
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 登录
    login () {
      this.$router.push('/sign')
    },
    // 获取验证码
    getCheckCode () {
      // 验证码倒计时
      this.setTime()
      // 掉用接口向指定邮箱发送验证码
      // 邮箱
      console.log(this.registerForm.email)
      this.$axios.get(`/emailLogin/${this.registerForm.email}`).then((res) => {
        if (res.data.code === 400) {
          this.$message.error('验证码发送失败')
        }
        this.$message.success('验证码发送成功')
      })
    },
    // 验证码倒计时
    async setTime () {
      let num = 60
      this.time = num-- + 's'
      clearInterval(this.timer)
      this.timer = await setInterval(() => {
        // 获取元素
        this.time = num-- + 's'
        num === 0 && (this.time = '获取验证码') && clearInterval(this.timer)
      }, 1000)
    },
  },
}
</script>
<style lang="less" scoped>
.register-container {
  width: 100%;
  height: 100vh;
  .register-box {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 960px;
    margin: 0 auto;
    height: 100%;
    .card-img {
      width: 100%;
      height: 200px;
      overflow: hidden;
      &:hover img {
        transform: scale(1.1);
      }
    }
    .check-code-box {
      display: flex;
      justify-content: space-between;
      .code-btn {
        width: 106px;
        height: 40px;
        margin-right: 15px;
      }
    }
  }
}
</style>

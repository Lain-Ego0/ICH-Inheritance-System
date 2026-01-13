<template>
    <div class="forget-container">
        <!-- 居中找回密码区域 -->
        <div class="center-section animate__animated animate__fadeInUp">
            <div class="forget-box">
                <div class="forget-header">
                    <h2 class="forget-title">找回密码</h2>
                    <p class="forget-subtitle">请填写以下信息进行身份验证</p>
                </div>

                <el-form ref="loginForm" :model="formData" :rules="rules" label-position="top">
                    <el-form-item label="账号" prop="UserName">
                        <el-input v-model.trim="formData.UserName" placeholder="请输入账号" prefix-icon="el-icon-user" />
                    </el-form-item>

                    <el-form-item label="邮箱" prop="Email">
                        <el-input v-model.trim="formData.Email" placeholder="请输入邮箱" prefix-icon="el-icon-message" />
                    </el-form-item>

                    <el-form-item label="联系方式" prop="PhoneNumber">
                        <el-input v-model.trim="formData.PhoneNumber" placeholder="请输入联系方式" prefix-icon="el-icon-phone" />
                    </el-form-item>

                    <el-form-item label="新密码" prop="Password">
                        <el-input type="password" v-model.trim="formData.Password" placeholder="请输入新密码"
                            prefix-icon="el-icon-lock" show-password />
                    </el-form-item>

                    <el-form-item label="验证码" prop="Code">
                        <div class="verify-code">
                            <el-input v-model.trim="formData.Code" placeholder="请输入验证码" />
                            <div class="code-img">
                                <ValidCode ref="ValidCode" />
                            </div>
                        </div>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" class="submit-btn" @click="ForgetBtn">
                            确认修改
                        </el-button>
                    </el-form-item>
                </el-form>

                <div class="forget-footer">
                    <div class="login-link">
                        <span>已有账号？</span>
                        <RouterLink to="/Login" class="link">返回登录</RouterLink>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import ValidCode from '@/components/Code/canvas.vue';
export default {
    components: {
        ValidCode: ValidCode
    },
    data() {
        return {
            formData: {
                UserName: '',
                Password: '',

                Code: ""
            },

            rules: {
                UserName: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                ],
                Password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ],
                "Email": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                    {
                        validator: (rule, value, callback) => {
                            var reg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
                            if (!value || !reg.test(value)) {
                                callback(new Error('请输入正确邮箱'));
                            }
                            else {
                                callback();
                            }
                        }, trigger: 'blur'
                    },
                ],
                "ImageUrls": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Name": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],

                "PhoneNumber": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                    {
                        validator: (rule, value, callback) => {
                            var reg = /^1[34578]\d{9}$/;
                            if (!value || !reg.test(value)) {
                                callback(new Error('请输入正确的手机号'));
                            }
                            else {
                                callback();
                            }
                        }, trigger: 'blur'
                    },
                ],
                Code: [
                    { required: true, message: '请输入验证码', trigger: 'blur' },
                    {
                        validator: (rule, value, callback) => {
                            let identifyCode = this.$refs.ValidCode.getCode();

                            if (value != identifyCode) {
                                callback(new Error('请输入正确的验证码'));
                            } else {
                                callback();
                            }
                        }, trigger: 'blur'
                    }
                ]
            }
        }

    },
    created() {

    },
    methods: {

        ForgetBtn() {
            this.$refs.loginForm.validate(async (valid) => {
                if (valid) {

                    let res = await this.$Post("/User/ForgetPassword", this.formData)
                    console.log(res);
                    if (res.Success) {
                        this.$message.success("修改密码成功!");
                        this.$router.push({
                            path: "/Login"
                        })
                    }
                    else {

                        this.$refs.ValidCode.refreshCode();
                    }
                } else {
                    this.$message.error("验证不通过")
                    this.$refs.ValidCode.refreshCode();
                    return false;
                }
            });
        }
    }
}
</script>

<style scoped lang="scss">
.forget-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    width: 100%;
    background-image: url('@/assets/login2.png');
    background-size: cover;
    background-position: center;
    background-attachment: fixed;
    position: relative;
}

.forget-container::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.4);
    z-index: 1;
}

.center-section {
    position: relative;
    z-index: 2;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    min-height: 100vh;
    padding: 2vh 2vw;

    .forget-box {
        width: 90%;
        max-width: 460px;
        padding: 4vh 3vw;
        background: rgba(255, 255, 255, 0.95);
        backdrop-filter: blur(20px);
        border-radius: 20px;
        box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
        border: 1px solid rgba(255, 255, 255, 0.2);
    }

    .forget-header {
        text-align: center;
        margin-bottom: 3vh;

        .forget-title {
            font-size: 2vw;
            color: #333;
            margin-bottom: 1vh;
        }

        .forget-subtitle {
            color: #666;
            font-size: 0.9vw;
        }
    }

    .verify-code {
        display: flex;
        gap: 15px;

        .code-img {
            flex-shrink: 0;
        }
    }

    .submit-btn {
        width: 100%;
        height: 44px;
        font-size: 1vw;
        margin-top: 2vh;
        border-radius: 22px;
        transition: all 0.3s ease;

        &:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1);
        }
    }

    .forget-footer {
        display: flex;
        justify-content: center;
        margin: 2vh 0;
        color: #666;
        font-size: 0.8vw;

        .link {
            color: var(--primary-color);
            text-decoration: none;
            margin-left: 8px;
            transition: color 0.3s ease;

            &:hover {
                color: var(--primary-color-light);
            }
        }
    }
}

/* 动画相关样式 */
.animate__animated {
    animation-duration: 1s;
    animation-fill-mode: both;
}

.animate__fadeInUp {
    animation-name: fadeInUp;
}

@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(30px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style>


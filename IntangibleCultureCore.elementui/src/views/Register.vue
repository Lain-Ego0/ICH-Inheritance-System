<template>
    <div class="register-container">
        <!-- 居中注册区域 -->
        <div class="center-section animate__animated animate__fadeInUp">
            <div class="register-box">
                <div class="register-header">
                    <h2 class="register-title">注册信息填写</h2>
                    <p class="register-subtitle">填写以下信息完成注册</p>
                </div>

                <el-form ref="loginForm" :model="formData" :rules="rules" label-position="left">
                    <el-form-item label="账号" prop="UserName">
                        <el-input v-model.trim="formData.UserName" placeholder="请输入账号" prefix-icon="el-icon-user" />
                    </el-form-item>

                    <el-form-item label="密码" prop="Password">
                        <el-input v-model.trim="formData.Password" type="password" placeholder="请输入密码"
                            prefix-icon="el-icon-lock" show-password />
                    </el-form-item>

                    <el-form-item label="邮箱" prop="Email">
                        <el-input v-model.trim="formData.Email" placeholder="请输入邮箱" prefix-icon="el-icon-message" />
                    </el-form-item>

                    <el-form-item label="联系方式" prop="PhoneNumber">
                        <el-input v-model.trim="formData.PhoneNumber" placeholder="请输入联系方式" prefix-icon="el-icon-phone" />
                    </el-form-item>

                    <el-form-item label="姓名" prop="Name">
                        <el-input v-model.trim="formData.Name" placeholder="请输入姓名" prefix-icon="el-icon-user" />
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
                        <el-button type="primary" class="register-btn" @click="RegisterBtn">
                            立即注册
                        </el-button>
                    </el-form-item>
                </el-form>

                <div class="register-footer">
                    <div class="login-link">
                        <span>已有账号？</span>
                        <RouterLink to="/Login" class="link">立即登录</RouterLink>
                    </div>
                    <RouterLink to="/ForgetPassword" class="link">忘记密码？</RouterLink>
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
                RoleType: "2",
                Code: ""
            },
            roleOptions: [],
            rules: {
                UserName: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                ],
                Password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ],
                "Email": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
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
        this.GetRoleTypeApi();
    },
    methods: {
        async GetRoleTypeApi() {
            let { Data: { Items } } = await this.$Post("/Select/RoleType");

            this.roleOptions = Items
            console.log(this.roleOptions)
        },
        RegisterBtn() {
            this.$refs.loginForm.validate(async (valid) => {
                if (valid) {

                    let res = await this.$Post("/User/Register", this.formData)
                    console.log(res);
                    if (res.Success) {
                        this.$message.success("注册成功!");
                        this.$router.push({
                            path: "/Login"
                        })
                    }
                    else {

                        this.$refs.ValidCode.refreshCode();
                    }
                } else {
                    this.$message.error("注册验证不通过")
                    this.$refs.ValidCode.refreshCode();
                    return false;
                }
            });
        }
    }
}
</script>

<style scoped lang="scss">
.register-container {
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

.register-container::before {
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

    .register-box {
        width: 90%;
        max-width: 460px;
        padding: 4vh 3vw;
        background: rgba(255, 255, 255, 0.95);
        backdrop-filter: blur(20px);
        border-radius: 20px;
        box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
        border: 1px solid rgba(255, 255, 255, 0.2);
    }

    .register-header {
        text-align: center;
        margin-bottom: 3vh;

        .register-title {
            font-size: 2vw;
            color: #333;
            margin-bottom: 1vh;
        }

        .register-subtitle {
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

    .register-btn {
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

    .register-footer {
        display: flex;
        justify-content: space-between;
        margin: 2vh 0;
        color: #666;
        font-size: 0.8vw;

        .link {
            color: var(--primary-color);
            text-decoration: none;
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

.animate__fadeInLeft {
    animation-name: fadeInLeft;
}

.animate__fadeInRight {
    animation-name: fadeInRight;
}

.animate__fadeInUp {
    animation-name: fadeInUp;
}

.animate__delay-1s {
    animation-delay: 0.5s;
}

.animate__delay-2s {
    animation-delay: 1s;
}

@keyframes fadeInLeft {
    from {
        opacity: 0;
        transform: translateX(-100px);
    }

    to {
        opacity: 1;
        transform: translateX(0);
    }
}

@keyframes fadeInRight {
    from {
        opacity: 0;
        transform: translateX(100px);
    }

    to {
        opacity: 1;
        transform: translateX(0);
    }
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
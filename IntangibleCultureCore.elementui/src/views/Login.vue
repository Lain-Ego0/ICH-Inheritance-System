<template>
    <div class="login-container">
        <!-- 居中登录区域 -->
        <div class="center-section animate__animated animate__fadeInUp">
            <div class="login-box">
                <div class="login-header">
                    <h2 class="login-title">欢迎登录 👋</h2>
                    <p class="login-subtitle">非遗文化管理平台</p>

                </div>

                <el-form ref="loginForm" :model="formData" :rules="rules" label-position="top">
                    <el-form-item label="账号" prop="UserName">
                        <el-input v-model.trim="formData.UserName" placeholder="请输入您的账号" prefix-icon="el-icon-user" />
                    </el-form-item>

                    <el-form-item label="密码" prop="Password">
                        <el-input v-model.trim="formData.Password" type="password" placeholder="请输入您的密码"
                            prefix-icon="el-icon-lock" show-password />
                    </el-form-item>

                    <el-form-item label="角色类型" prop="RoleType">
                        <el-radio-group v-model="formData.RoleType">
                            <el-radio v-for="item in roleOptions" :key="item.Code" :label="item.Code">
                                {{ item.Label }}
                            </el-radio>
                        </el-radio-group>
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
                        <el-button type="primary" class="login-btn" @click="LoginBtn">
                            登录系统
                        </el-button>
                    </el-form-item>
                </el-form>

                <div class="login-footer">
                    <div class="register-link">
                        <span>还没有账号？</span>
                        <RouterLink to="/Register" class="link">立即注册</RouterLink>
                    </div>
                    <RouterLink to="/ForgetPassword" class="link">忘记密码？</RouterLink>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import ValidCode from '@/components/Code/canvas.vue';
import store from '@/store';
export default {
    components: {
        ValidCode: ValidCode
    },
    data() {
        return {
            formData: {
                UserName: '',
                Password: '',
                RoleType: "",
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
                RoleType: [
                    { required: true, message: '请选择角色', trigger: 'blur' },
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

        },
        LoginBtn() {
            this.$refs.loginForm.validate(async (valid) => {
                if (valid) {

                    let res = await store.dispatch("Login", this.formData);
                    if (res.Success) {
                        this.$message.success("登录成功!");
                        this.$router.push({
                            path: "/Admin"
                        })

                    }

                } else {
                    this.$message.error("登录验证不通过")

                    return false;
                }
            });
        }
    }
}
</script>

<style scoped lang="scss">
.login-container {
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

.login-container::before {
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

    .login-box {
        width: 90%;
        max-width: 460px;
        padding: 4vh 3vw;
        background: rgba(255, 255, 255, 0.95);
        backdrop-filter: blur(20px);
        border-radius: 20px;
        box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
        border: 1px solid rgba(255, 255, 255, 0.2);
    }

    .login-header {
        text-align: center;
        margin-bottom: 3vh;

        .login-title {
            font-size: 2vw;
            color: #333;
            margin-bottom: 1vh;
        }

        .login-subtitle {
            color: #666;
            font-size: 0.9vw;
            margin-bottom: 1vh;
        }

        .platform-features {
            display: flex;
            justify-content: center;
            gap: 20px;
            color: #666;
            font-size: 0.8vw;
        }
    }

    .verify-code {
        display: flex;
        gap: 15px;

        .code-img {
            flex-shrink: 0;
        }
    }

    .login-btn {
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

    .login-footer {
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

.animate__delay-3s {
    animation-delay: 1.5s;
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
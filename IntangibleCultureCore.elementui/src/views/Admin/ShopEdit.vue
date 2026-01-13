<template>
    <div class="app-container">
        <el-card class="shop-edit-card">
            <!-- 页面标题区域 -->
            <div class="page-header">
                <h2 class="page-title">店铺信息维护</h2>
                <div class="page-desc">请完善以下店铺信息，带 * 为必填项</div>
            </div>

            <!-- 表单区域 -->
            <el-form v-if="editorShow" ref="editModalForm" :rules="editModalFormRules" :model="formData" label-width="140px"
                size="medium">

                <!-- 基础信息板块 -->
                <div class="form-section">
                    <div class="section-title">基础信息</div>
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <el-form-item label="店铺名称" prop="Name">
                                <el-input v-model.trim="formData.Name" placeholder="请输入店铺名称" clearable></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="联系电话" prop="Phone">
                                <el-input v-model.trim="formData.Phone" placeholder="请输入联系电话" clearable></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="邮箱地址" prop="Email">
                                <el-input v-model.trim="formData.Email" placeholder="请输入邮箱地址" clearable></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="店铺地址" prop="Address">
                                <el-input v-model.trim="formData.Address" placeholder="请输入店铺地址" clearable></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </div>

                <!-- 店铺形象板块 -->
                <div class="form-section">
                    <div class="section-title">店铺形象</div>
                    <el-row :gutter="20">
                        <el-col :span="12">
                            <el-form-item label="店铺Logo" prop="LogoCover">
                                <UploadImages :limit="1" v-model="formData.LogoCover"></UploadImages>
                                <div class="form-tips">建议尺寸：200x200px，支持jpg、png格式</div>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="店铺保障" prop="Ensure">
                                <el-input v-model.trim="formData.Ensure" placeholder="请输入店铺保障信息" clearable></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </div>

                <!-- 详细介绍板块 -->
                <div class="form-section">
                    <div class="section-title">详细介绍</div>
                    <el-form-item label="店铺详情" prop="Content">
                        <QillRichText v-model="formData.Content"></QillRichText>
                    </el-form-item>
                </div>

                <!-- 提交按钮区域 -->
                <div class="form-actions">
                    <el-button type="primary" @click="CreateOrEditForm">保存信息</el-button>
                    <el-button @click="$router.go(-1)">返回</el-button>
                </div>
            </el-form>
        </el-card>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
    name: "ShopList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    data() {

        return {
            searchForm: {},//搜索定义的数据对象

            editModalFormRules: {
                "Name": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "UserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "AuditUserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "AuditStatus": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "LogoCover": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Address": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Email": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Phone": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "AuditReason": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Ensure": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Content": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
            },
            formData: {

            },//保存或者修改定义的数据对象
            editorShow: false,

        };
    },
    created() {
        this.ShowEditModal();
    },
    methods: {

        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/Shop/Get`, { Id: 0, UserId: this.UserId });
            if (Data.Id == null) {
                Data.UserId = this.UserId;
                Data.Email = this.UserInfo.Email;
                Data.Phone = this.UserInfo.PhoneNumber;
                Data.AuditStatus = 2;
            }
            this.formData = Data;

            this.editorShow = true;

        },
        /**
         * 点击保存的时候会触发
         */
        async CreateOrEditForm() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {

                    var { Success } = await this.$Post(`/Shop/CreateOrEdit`, this.formData);

                    if (Success) {
                        this.$message.success("恭喜你,操作成功");
                    }
                }
            })
        },


    },
};
</script>

<style lang="scss" scoped>
.shop-edit-card {
    /* 卡片样式 */
    margin: 20px;

    .page-header {
        margin-bottom: 30px;
        padding-bottom: 20px;
        border-bottom: 1px solid #ebeef5;

        .page-title {
            font-size: 20px;
            color: #303133;
            margin: 0 0 10px 0;
            font-weight: 500;
        }

        .page-desc {
            font-size: 14px;
            color: #909399;
        }
    }

    .form-section {
        margin-bottom: 30px;

        .section-title {
            font-size: 16px;
            font-weight: 500;
            color: #303133;
            margin-bottom: 20px;
            padding-left: 10px;
            border-left: 4px solid var(--primary-color);
        }
    }

    .form-tips {
        font-size: 12px;
        color: #909399;
        margin-top: 5px;
    }

    .form-actions {
        margin-top: 40px;
        padding-top: 20px;
        border-top: 1px solid #ebeef5;
        text-align: center;

        .el-button {
            padding: 12px 35px;
            margin: 0 10px;
        }
    }
}

/* 表单项样式优化 */
.el-form-item {
    margin-bottom: 22px;

}

/* 输入框样式优化 */
.el-input {}
</style>

<style scoped>
/* 统一按钮颜色为侧边栏风格 */
.el-button--primary {
    background-color: #333;
    border-color: #333;
    color: #fff;
}

.el-button--primary:hover {
    background-color: #555;
    border-color: #555;
}

.el-button--warning {
    background-color: #333;
    border-color: #333;
    color: #fff;
}

.el-button--warning:hover {
    background-color: #555;
    border-color: #555;
}

.el-button--danger {
    background-color: #333;
    border-color: #333;
    color: #fff;
}

.el-button--danger:hover {
    background-color: #555;
    border-color: #555;
}
</style>
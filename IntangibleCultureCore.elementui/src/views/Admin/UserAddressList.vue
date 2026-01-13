<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <el-button type="primary" size="mini" icon="el-icon-search" @click="SearchClick">搜 索</el-button>
                <el-button type="warning" size="mini" @click="ResetClick" icon="el-icon-s-promotion">重 置</el-button>
            </div>
            <div class="tb-body">
                <el-form ref="searchFormRef" :model="searchForm" :inline="true" label-width="120px" :style="`flex:1;`">
                    <el-form-item label="收货姓名" prop="NameLike">
                        <el-input v-model.trim="searchForm.NameLike" placeholder="请输入收货姓名" :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="电话" prop="PhoneLike">
                        <el-input v-model.trim="searchForm.PhoneLike" placeholder="请输入电话" :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="省" prop="ProviceLike">
                        <el-input v-model.trim="searchForm.ProviceLike" placeholder="请输入省" :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="市" prop="CityLike">
                        <el-input v-model.trim="searchForm.CityLike" placeholder="请输入市" :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="区" prop="AreaLike">
                        <el-input v-model.trim="searchForm.AreaLike" placeholder="请输入区" :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="性别" prop="SexLike">
                        <el-input v-model.trim="searchForm.SexLike" placeholder="请输入性别" :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="门牌号" prop="HouseNumberLike">
                        <el-input v-model.trim="searchForm.HouseNumberLike" placeholder="请输入门牌号"
                            :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="所属用户">
                        <SigleSelect url="/User/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.BelongUserId">
                        </SigleSelect>
                    </el-form-item>
                </el-form>
            </div>

        </el-card>


        <el-dialog :title="formData.Id ? '修改收货地址' : '添加收货地址'" :visible.sync="editorShow" width="50%" :lock-scroll="true"
            height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                <el-row :gutter="10" class="EditFromBody" style="  height: 600px;overflow-y: scroll">
                    <el-col :span="24">
                        <el-form-item label="收货姓名" prop="Name">
                            <el-input type="text" v-model.trim="formData.Name" placeholder="请输入收货姓名"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="所属用户" prop="BelongUserId">
                            <SigleSelect url="/User/List" columnName="Name" columnValue="Id"
                                v-model="formData.BelongUserId">
                            </SigleSelect>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="电话" prop="Phone">
                            <el-input type="text" v-model.trim="formData.Phone" placeholder="请输入电话"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="省" prop="Provice">
                            <el-input type="text" v-model.trim="formData.Provice" placeholder="请输入省"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="市" prop="City">
                            <el-input type="text" v-model.trim="formData.City" placeholder="请输入市"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="区" prop="Area">
                            <el-input type="text" v-model.trim="formData.Area" placeholder="请输入区"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="性别" prop="Sex">
                            <el-input type="text" v-model.trim="formData.Sex" placeholder="请输入性别"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="门牌号" prop="HouseNumber">
                            <el-input type="text" v-model.trim="formData.HouseNumber" placeholder="请输入门牌号"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>


                <el-row type="flex" justify="end" align="bottom">
                    <el-form-item>
                        <el-button type="primary" plain @click="CreateOrEditForm()">确 定</el-button>
                        <el-button @click="editorShow = false">取 消</el-button>
                    </el-form-item>
                </el-row>
            </el-form>

        </el-dialog>


        <PaginationTable ref="PaginationTableId" url="/UserAddress/List" :column="dataColum">
            <template v-slot:header>
                <el-button type="primary" size="mini" plain icon="el-icon-edit" @click="ShowEditModal()">新 增</el-button>
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="ShowBatchDeleteModal()">批 量 删
                    除</el-button>
                <ExportButton :where="searchForm" exportUrl="/UserAddress/Export"></ExportButton>

            </template>
            <template v-slot:Operate="scope">
                <el-button type="primary" size="mini" @click="ShowEditModal(scope.row.Id)">修 改</el-button>
                <el-button type="danger" size="mini" @click="ShowDeleteModal(scope.row.Id)">删 除</el-button>
            </template>
        </PaginationTable>
    </div>
</template>

<script>
import store from '@/store';
import { mapGetters } from 'vuex'
export default {
    name: "UserAddressList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    data() {

        return {
            searchForm: {},//搜索定义的数据对象
            dataColum: [
                {
                    key: "Id",
                    hidden: true,

                },
                {
                    key: "Name",
                    title: "收货姓名",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Phone",
                    title: "电话",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Provice",
                    title: "省",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "City",
                    title: "市",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Area",
                    title: "区",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "BelongUserId",
                    hidden: true,
                },
                {
                    key: "BelongUserDto.Name",
                    title: "用户名称",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Sex",
                    title: "性别",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "HouseNumber",
                    title: "门牌号",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    title: "操作",
                    width: "300px",
                    key: "Operate",
                    type: store.getters.ColumnType.USERDEFINED,
                },
            ],
            editModalFormRules: {
                "Name": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "BelongUserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Phone": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Provice": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "City": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Area": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Sex": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "HouseNumber": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
            },
            formData: {

            },//保存或者修改定义的数据对象
            editorShow: false,

        };
    },
    created() {

    },
    methods: {
        //修改属性
        async UpdateEntityAsync(Id, title, data) {
            let { Data } = await this.$PostSigleUpdate(`/UserAddress/Get`, `/UserAddress/CreateOrEdit`, Id, title, data);

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/UserAddress/Get`, { Id: Id });
            this.formData = Data;

            this.editorShow = true;

        },
        /**
         * 点击保存的时候会触发
         */
        async CreateOrEditForm() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/UserAddress/CreateOrEdit`, this.formData);

                    if (Success) {
                        this.editorShow = false;
                        this.$refs.PaginationTableId.Reload(this.searchForm);
                    }
                }
            })
        },
        /**
         * 单个删除的时候会触发
         */
        async ShowDeleteModal(Id) {
            await this.$PostDelete(`/UserAddress/Delete`, { Id: Id });
            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 批量删除的时候会触发
         */
        async ShowBatchDeleteModal() {
            var ids = this.$refs.PaginationTableId.GetSelectionRow().map(x => x.Id);
            if (ids.length == 0) {
                this.$message.error("你选择需要删除的记录");
                return;
            }
            await this.$PostDelete(`/UserAddress/BatchDelete`, { Ids: ids });

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击表格搜索按钮会触发
         */
        async SearchClick() {
            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击清空表单会触发
         */
        async ResetClick() {
            this.searchForm = {};
            this.$refs.PaginationTableId.Reload(this.searchForm);
        },




    },
};
</script>

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
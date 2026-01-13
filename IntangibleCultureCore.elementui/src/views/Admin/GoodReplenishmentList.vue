<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <el-button type="primary" size="mini" icon="el-icon-search" @click="SearchClick">搜 索</el-button>
                <el-button type="warning" size="mini" @click="ResetClick" icon="el-icon-s-promotion">重 置</el-button>
            </div>
            <div class="tb-body">
                <el-form ref="searchFormRef" :model="searchForm" :inline="true" label-width="120px" :style="`flex:1;`">
                    <el-form-item label="名称" prop="NameLike">
                        <el-input v-model.trim="searchForm.NameLike" placeholder="请输入名称" :clearable="true"></el-input>
                    </el-form-item>



                    <el-form-item label="分类">
                        <SigleSelect url="/GoodType/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.GoodTypeId" :where="{ IsShop: false }">
                        </SigleSelect>
                    </el-form-item>

                </el-form>
            </div>

        </el-card>

        <el-dialog :title="'文创品补货'" :visible.sync="editorShow" width="50%" :lock-scroll="true" height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                <el-row :gutter="10" class="EditFromBody">
                    <el-col :span="24">
                        <el-form-item label="文创品" prop="GoodId">
                            <SigleSelect url="/Good/List" columnName="Name" columnValue="Id" v-model="formData.GoodId"
                                :where="{ ShopId: ShopId }" :disabled="true">
                            </SigleSelect>
                        </el-form-item>
                    </el-col>


                    <el-col :span="24">
                        <el-form-item label="调整数量" prop="Qty">
                            <el-input type="number" v-model.trim="formData.Qty" placeholder="请输入调整数量"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="备注" prop="Remark">
                            <el-input type="textarea" v-model.trim="formData.Remark" placeholder="请输入备注"
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


        <PaginationTable ref="PaginationTableId" url="/Good/List" :column="dataColum" :where="where">
            <template v-slot:header>


            </template>
            <template v-slot:Operate="scope">
                <el-button type="primary" size="mini" @click="ShowEditModal(scope.row.Id)">补 货</el-button>


            </template>
        </PaginationTable>
    </div>
</template>

<script>
import store from '@/store';
import { mapGetters } from 'vuex';
export default {
    name: "GoodList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId", "ShopId"
        ])
    },
    data() {

        let where = {};

        where.IsReplenishment = true;

        return {
            where: where,
            searchForm: {},//搜索定义的数据对象
            dataColum: [
                {
                    key: "Id",
                    hidden: true,

                },

                {
                    key: "Name",
                    title: "文创品名称",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "GoodTypeDto.Name",
                    title: "文创品分类",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Cover",
                    title: "封面",
                    type: store.getters.ColumnType.IMAGES,
                },


                {
                    key: "Stock",
                    title: "库存",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "ReplenishmentLimit",
                    title: "库存下限",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },

                {
                    key: "Content",
                    title: "详情内容",
                    width: "140px",
                    type: store.getters.ColumnType.RICHTEXT,
                },
                {
                    key: "ShopId",
                    hidden: true,
                },

                {
                    key: "UserId",
                    hidden: true,
                },

                {
                    key: "Unit",
                    title: "单位",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },


                {
                    key: "GoodTypeId",
                    hidden: true,
                },

                {
                    title: "操作",
                    width: "300px",
                    key: "Operate",
                    type: store.getters.ColumnType.USERDEFINED,
                },
            ],
            editModalFormRules: {
                "GoodId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ShopId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Remark": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Qty": [
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
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(GoodId) {

            let { Data } = await this.$Post(`/GoodStock/Get`, { Id: null });

            Data.ShopId = this.ShopId;
            Data.GoodId = GoodId;

            this.formData = Data;

            this.editorShow = true;

        },
        /**
        * 点击保存的时候会触发
        */
        async CreateOrEditForm() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/GoodStock/CreateOrEdit`, this.formData);

                    if (Success) {
                        this.editorShow = false;
                        this.$refs.PaginationTableId.Reload(this.searchForm);
                    }
                }
            })
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
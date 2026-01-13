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
                    <el-form-item label="单位" prop="UnitLike">
                        <el-input v-model.trim="searchForm.UnitLike" placeholder="请输入单位" :clearable="true"></el-input>
                    </el-form-item>


                    <el-form-item label="分类">
                        <SigleSelect url="/GoodType/List" columnName="Name" :clearable="true" columnValue="Id"
                            v-model="searchForm.GoodTypeId">
                        </SigleSelect>
                    </el-form-item>

                </el-form>
            </div>

        </el-card>


        <el-dialog :title="formData.Id ? '修改文创品' : '添加文创品'" :visible.sync="editorShow" width="50%" :lock-scroll="true"
            height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                <el-row :gutter="10" class="EditFromBody" style="  height: 600px;overflow-y: scroll">
                    <el-col :span="24">
                        <el-form-item label="名称" prop="Name">
                            <el-input type="text" v-model.trim="formData.Name" placeholder="请输入名称"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>


                    <el-col :span="24">
                        <el-form-item label="分类" prop="GoodTypeId">
                            <SigleSelect url="/GoodType/List" columnName="Name" columnValue="Id"
                                v-model="formData.GoodTypeId">
                            </SigleSelect>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="封面" prop="Cover">
                            <UploadImages :limit="1" v-model="formData.Cover"></UploadImages>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="主图" prop="ImageUrls">
                            <UploadImages :limit="7" v-model="formData.ImageUrls"></UploadImages>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="单位" prop="Unit">
                            <el-input type="text" v-model.trim="formData.Unit" placeholder="请输入单位"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="价格" prop="Price">
                            <el-input type="number" v-model.trim="formData.Price" placeholder="请输入价格"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="库存下限" prop="ReplenishmentLimit">
                            <el-input type="number" v-model.trim="formData.ReplenishmentLimit" placeholder="请输入库存下限"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="属性列表">
                            <div class="prop-list">
                                <div v-for="(item, index) in formData.GoodPropDtoList" :key="index" class="prop-item">
                                    <el-input v-model="item.Name" placeholder="请输入属性名称"
                                        style="width: 200px; margin-right: 10px">
                                    </el-input>
                                    <el-input v-model="item.Value" placeholder="请输入属性值"
                                        style="width: 200px; margin-right: 10px">
                                    </el-input>
                                    <el-button type="danger" size="mini" icon="el-icon-delete"
                                        @click="removeProp(index)">删除</el-button>
                                </div>
                                <el-button type="primary" size="mini" icon="el-icon-plus" @click="addProp">添加属性</el-button>
                            </div>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="详情内容" prop="Content">
                            <QillRichText v-model="formData.Content"></QillRichText>
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
                <el-button type="primary" size="mini" plain icon="el-icon-edit" @click="ShowEditModal()">新 增</el-button>
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="ShowBatchDeleteModal()">批 量 删
                    除</el-button>
                <ExportButton :where="{ ...where, ...searchForm }" exportUrl="/Good/Export">
                </ExportButton>

            </template>
            <template v-slot:Operate="scope">
                <el-button type="primary" size="mini" @click="ShowEditModal(scope.row.Id)">修 改</el-button>

                <el-button type="danger" size="mini" @click="ShowDeleteModal(scope.row.Id)">删
                    除</el-button>
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
        if (store.getters.RoleType == "商家") {
            where.ShopId = store.getters.ShopId;
        }
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
                    title: "名称",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "GoodTypeDto.Name",
                    title: "文创品分类名称",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "Cover",
                    title: "封面",
                    type: store.getters.ColumnType.IMAGES,
                },
                {
                    key: "ImageUrls",
                    title: "主图",
                    width: "450px",
                    type: store.getters.ColumnType.IMAGES,
                },
                {
                    key: "Price",
                    title: "价格",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
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
                    key: "ShopDto.Name",
                    title: "店铺名称",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
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
                "Name": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ShopId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "UserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],

                "GoodTypeId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],

                "Cover": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ImageUrls": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Unit": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "Price": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ReplenishmentLimit": [
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

    },
    methods: {
        //前往文创品属性
        async ToGoodProp(Id) {
            this.$router.push({
                path: "/Admin/GoodPropList",
                query: {
                    GoodId: Id
                }
            })
        },
        //修改属性
        async UpdateEntityAsync(Id, title, data) {
            let { Data } = await this.$PostSigleUpdate(`/Good/Get`, `/Good/CreateOrEdit`, Id, title, data);

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {
            let { Data } = await this.$Post(`/Good/Get`, { Id: Id });
            Data.IsEdit = Id > 0;
            if (Data.IsEdit == false) {
                Data.AuditStatus = 0;
                Data.Stock = 0;
                Data.ReplenishmentLimit = 0;
                Data.ShopId = this.ShopId;
                Data.GoodPropDtoList = []; // 初始化空数组
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
                    var { Success } = await this.$Post(`/Good/CreateOrEdit`, this.formData);

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
            await this.$PostDelete(`/Good/Delete`, { Id: Id });
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
            await this.$PostDelete(`/Good/BatchDelete`, { Ids: ids });

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
        // 添加新属性
        addProp() {
            if (!this.formData.GoodPropDtoList) {
                this.formData.GoodPropDtoList = [];
            }
            this.formData.GoodPropDtoList.push({
                Name: '',
                Value: '',
                GoodId: this.formData.Id
            });
        },
        // 删除属性
        removeProp(index) {
            this.formData.GoodPropDtoList.splice(index, 1);
        },
    },
};
</script>

<style scoped>
/* 属性列表样式 */
.prop-list {
    padding: 10px;
    border: 1px solid #EBEEF5;
    border-radius: 4px;
}

.prop-item {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
}

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
<template>
    <div class="app-container">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <el-button type="primary" size="mini" icon="el-icon-search" @click="SearchClick">搜 索</el-button>
                <el-button type="warning" size="mini" @click="ResetClick" icon="el-icon-s-promotion">重 置</el-button>
            </div>
            <div class="tb-body">
                <el-form ref="searchFormRef" :model="searchForm" :inline="true" label-width="120px" :style="`flex:1;`">
                    <el-form-item label="订单编号" prop="OrderNoLike">
                        <el-input v-model.trim="searchForm.OrderNoLike" placeholder="请输入订单编号" :clearable="true"></el-input>
                    </el-form-item>

                    <el-form-item label="物流单号" prop="LogisticsNoLike">
                        <el-input v-model.trim="searchForm.LogisticsNoLike" placeholder="请输入物流单号"
                            :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="收货地址" prop="ReceiveAddressLike">
                        <el-input v-model.trim="searchForm.ReceiveAddressLike" placeholder="请输入收货地址"
                            :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="收货电话" prop="ReceivePhoneLike">
                        <el-input v-model.trim="searchForm.ReceivePhoneLike" placeholder="请输入收货电话"
                            :clearable="true"></el-input>
                    </el-form-item>
                    <el-form-item label="收货人" prop="ReceiveNameLike">
                        <el-input v-model.trim="searchForm.ReceiveNameLike" placeholder="请输入收货人"
                            :clearable="true"></el-input>
                    </el-form-item>


                </el-form>
            </div>

        </el-card>


        <el-dialog title="发货" :visible.sync="editorShow" width="50%" :lock-scroll="true" height="800px">
            <el-form v-if="editorShow == true" ref="editModalForm" :rules="editModalFormRules" :model="formData"
                label-width="140px" size="mini">
                <el-row :gutter="10" class="EditFromBody">

                    <el-col :span="24">
                        <el-form-item label="物流单号" prop="LogisticsNo">
                            <el-input type="text" v-model.trim="formData.LogisticsNo" placeholder="请输入物流单号"
                                :clearable="true"></el-input>
                        </el-form-item>
                    </el-col>

                </el-row>


                <el-row type="flex" justify="end" align="bottom">
                    <el-form-item>
                        <el-button type="primary" plain @click="SendGood()">确 定</el-button>
                        <el-button @click="editorShow = false">取 消</el-button>
                    </el-form-item>
                </el-row>
            </el-form>

        </el-dialog>


        <PaginationTable ref="PaginationTableId" url="/OrderInfo/List" :column="dataColum" :where="where">
            <template v-slot:header>
                <ExportButton :where="{ ...searchForm, ...where }" exportUrl="/OrderInfo/Export"></ExportButton>
            </template>
            <template v-slot:Operate="scope">
                <el-button type="primary" size="mini" v-if="scope.row.OrderStatus == 1"
                    @click="ShowEditModal(scope.row.Id)">发货</el-button>

                <el-button type="primary" size="mini" v-if="scope.row.OrderStatus == 8"
                    @click="SelfPickup(scope.row.Id)">到店取货确认</el-button>

                <el-button type="danger" size="mini" v-if="scope.row.OrderStatus == 4"
                    @click="DispatchOrderReturn(scope.row)">处理售后</el-button>

                <!-- <el-button type="danger" size="mini" @click="ShowDeleteModal(scope.row.Id)">删 除</el-button> -->
            </template>
        </PaginationTable>
    </div>
</template>

<script>
import store from '@/store';
import { mapGetters } from 'vuex';
export default {
    name: "OrderInfoList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    data() {
        let where = {};

        return {
            where: where,
            searchForm: {},//搜索定义的数据对象
            dataColum: [
                {
                    key: "Id",
                    hidden: true,

                },
                {
                    key: "OrderStatus",
                    hidden: true,

                },
                {
                    key: "OrderNo",
                    title: "订单编号",
                    width: "250px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "UserId",
                    hidden: true,
                },
                {
                    key: "UserDto.UserName",
                    title: "买家账号",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "GoodImages",
                    title: "文创品图片",
                    width: "250px",
                    type: store.getters.ColumnType.IMAGES,
                    template: function (item, index) {

                        return item.OrderDets.map(x => x.GoodDto.Cover).join(',')
                    }
                },
                {
                    key: "Goods",
                    title: "文创品",
                    width: "250px",
                    type: store.getters.ColumnType.SHORTTEXT,
                    template: function (item, index) {

                        return item.OrderDets.map(x => x.GoodDto.Name + "*" + x.Qty).join(',')
                    }
                },
                {
                    key: "ShopUserId",
                    hidden: true,
                },
                {
                    key: "LogisticsNo",
                    title: "物流单号",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "OrderStatusFormat",
                    title: "订单状态",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "ReceiveName",
                    title: "收货人",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "ReceivePhone",
                    title: "收货电话",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "ReceiveAddress",
                    title: "收货地址",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "SelfPickupTime",
                    title: "自提时间",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "IsSelfPickup",
                    title: "是否自提",
                    width: "150px",
                    type: store.getters.ColumnType.JUDGMENTTAG,
                },


                {
                    key: "ShopId",
                    hidden: true,
                },

                {
                    key: "TotalQty",
                    title: "总数量",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "OrginMoney",
                    title: "总价格",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "TotalMoney",
                    title: "实际支付金额",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "PayType",
                    title: "支付类型",
                    width: "150px",
                    type: store.getters.ColumnType.SHORTTEXT,
                },
                {
                    key: "PayTime",
                    title: "支付时间",
                    width: "240px",
                    type: store.getters.ColumnType.DATETIME,
                },
                // {
                //     key: "ExpireTime",
                //     title: "过期时间",
                //     width: "240px",
                //     type: store.getters.ColumnType.DATETIME,
                // },

                {
                    title: "操作",
                    width: "300px",
                    key: "Operate",
                    type: store.getters.ColumnType.USERDEFINED,
                },
            ],
            editModalFormRules: {
                "OrderNo": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "UserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ShopUserId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ShopId": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "PayTime": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ExpireTime": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "TotalQty": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "PayType": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "LogisticsNo": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ReceiveAddress": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ReceivePhone": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "ReceiveName": [
                    { required: true, message: '该项为必填项', trigger: 'blur' },
                ],
                "TotalMoney": [
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
        async DispatchOrderReturn(item) {
            this.$router.push({
                path: "/Admin/OrderReturnList",
                query: {
                    OrderId: item.Id
                }
            })
        },
        async SelfPickup(Id) {
            let { Data } = await this.$PostSigleUpdate(`/OrderInfo/Get`, `/OrderInfo/SelfPickup`, Id, "到店取货确认", {});

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        //修改属性
        async UpdateEntityAsync(Id, title, data) {
            let { Data } = await this.$PostSigleUpdate(`/OrderInfo/Get`, `/OrderInfo/CreateOrEdit`, Id, title, data);

            this.$refs.PaginationTableId.Reload(this.searchForm);
        },
        /**
         * 点击新增或者编辑的时候会触发
         */
        async ShowEditModal(Id) {

            let { Data } = await this.$Post(`/OrderInfo/Get`, { Id: Id });
            this.formData = Data;

            this.editorShow = true;

        },
        /**
         * 点击保存的时候会触发
         */
        async SendGood() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/OrderInfo/SendGood`, this.formData);

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
            await this.$PostDelete(`/OrderInfo/Delete`, { Id: Id });
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
            await this.$PostDelete(`/OrderInfo/BatchDelete`, { Ids: ids });

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
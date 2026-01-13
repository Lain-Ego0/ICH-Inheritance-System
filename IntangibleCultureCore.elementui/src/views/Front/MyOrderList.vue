<template>
    <div class="page-container ">
        <div class="card margin-top-xs margin-bottom-xs">
            <el-page-header @back="goBack()" content="我的订单">
            </el-page-header>
        </div>
        <div class="custom-tabs">
            <div v-for="tab in orderTabs" :key="tab.value" :class="['tab-item', { active: activeTab === tab.value }]"
                @click="handleTabClick(tab.value)">
                {{ tab.label }}
                <span class="tab-line" v-if="activeTab === tab.value"></span>
            </div>
        </div>


        <PaginationBox url="/OrderInfo/List" ref="PaginationTableId" :where="where">
            <template v-slot:content="{ data }">
                <div class="order-list">
                    <el-card v-for="order in data" :key="order.Id" class="order-item margin-bottom">
                        <!-- Order Header -->
                        <div class="order-header" style="border-bottom: 1px solid #eee; padding-bottom: 10px;">
                            <div class="flex justify-between align-center">
                                <div>
                                    <span class="font-bold">订单号：{{ order.OrderNo }}</span>
                                    <el-tag size="small" :type="getOrderStatusType(order.OrderStatus)" class="margin-left">
                                        {{ order.OrderStatusFormat }}
                                    </el-tag>
                                </div>
                                <span class="text-gray">下单时间：{{ order.CreationTime }}</span>
                            </div>
                        </div>

                        <!-- Shop Info -->
                        <div class="shop-info padding-y-sm" style="border-bottom: 1px solid #eee;">
                            <div class="flex align-center">
                                <img :src="order.ShopDto.LogoCover" class="shop-logo"
                                    style="width: 30px; height: 30px; border-radius: 50%;">
                                <span class="margin-left">{{ order.ShopDto.Name }}</span>
                            </div>
                        </div>

                        <!-- Products -->
                        <div class="products padding-y" style="border-bottom: 1px solid #eee;">
                            <div v-for="det in order.OrderDets" :key="det.Id" class="product-item flex margin-bottom-sm">
                                <img :src="det.GoodDto.Cover" style="width: 80px; height: 80px; object-fit: cover;">
                                <div class="margin-left flex-1">
                                    <div class="product-name">{{ det.GoodDto.Name }}</div>
                                    <div class="text-gray margin-top-xs">数量：{{ det.Qty }}{{ det.GoodDto.Unit }}</div>
                                    <div class="text-danger margin-top-xs">￥{{ det.Price }}</div>
                                </div>
                            </div>
                        </div>

                        <!-- Order Info -->
                        <div class="order-info padding-y" style="border-bottom: 1px solid #eee;">
                            <div class="flex justify-between margin-bottom-xs">
                                <span>支付方式：</span>
                                <span>{{ order.PayType }}</span>
                            </div>
                            <div class="flex justify-between margin-bottom-xs">
                                <span>收货信息：</span>
                                <span>{{ order.ReceiveName }} {{ order.ReceivePhone }}</span>
                            </div>
                            <div class="flex justify-between margin-bottom-xs">
                                <span>收货地址：</span>
                                <span>{{ order.ReceiveAddress }}</span>
                            </div>
                            <div class="flex justify-between text-danger font-bold">
                                <span>实付金额：</span>
                                <span>￥{{ order.TotalMoney }}</span>
                            </div>
                        </div>

                        <!-- Actions -->
                        <div class="order-actions padding-top text-right">
                            <el-button type="primary" size="mini" v-if="order.LogisticsNo"
                                @click="LookExpress(order)">查看物流</el-button>
                            <el-button type="primary" size="mini" v-if="order.OrderStatus == 2"
                                @click="GetGood(order.Id)">确认收货</el-button>
                            <el-button type="primary" size="mini" v-if="order.OrderStatus == 3"
                                @click="OrderComment(order)">去评价</el-button>
                            <el-button type="danger" size="mini" v-if="[1, 2, 3, 4].indexOf(order.OrderStatus) != -1"
                                @click="OrderReturn(order)">退款售后</el-button>

                        </div>
                    </el-card>
                </div>
            </template>
        </PaginationBox>

    </div>
</template>

<script>
import PaginationBox from "@/components/Pagination/PaginationBox.vue";
import store from '@/store';

import { mapGetters } from 'vuex';
export default {
    name: "OrderInfoList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    components: {

        PaginationBox
    },
    data() {

        let where = {
            UserId: store.getters.UserId,
        };
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
                    key: "ShopUserId",
                    hidden: true,
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
                    key: "OrderStatusFormat",
                    title: "订单状态",
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
                {
                    key: "ExpireTime",
                    title: "过期时间",
                    width: "240px",
                    type: store.getters.ColumnType.DATETIME,
                },
                {
                    key: "LogisticsNo",
                    title: "物流单号",
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
                    key: "ReceivePhone",
                    title: "收货电话",
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
            activeTab: -1,
            orderTabs: [
                { label: '全部', value: -1 },
                { label: '待支付', value: 0 },
                { label: '待发货', value: 1 },
                { label: '待收货', value: 2 },
                { label: '待评价', value: 3 },
                { label: '售后中', value: 4 },
                { label: '已退款', value: 5 },
                { label: '完成', value: 6 },
                { label: '订单关闭', value: 7 },
            ],
        };
    },
    created() {

    },
    methods: {

        //返回上一个页面
        goBack() {
            this.$router.go(-1);
        },
        //查看物流
        async LookExpress(item) {
            console.log(item);
            this.$router.push({
                path: "/Front/OrderExpressQuery",
                query: {
                    LogisticsNo: item.LogisticsNo
                }
            })
        },
        //订单评价
        async OrderComment(item) {
            this.$router.push({
                path: "/Front/OrderComment",
                query: {
                    OrderInfoId: item.Id
                }
            })
        },
        //退款售后
        async OrderReturn(item) {
            this.$router.push({
                path: "/Front/OrderReturn",
                query: {
                    OrderInfoId: item.Id
                }
            })
        },
        //收货
        async GetGood(Id) {

            let { Data } = await this.$PostSigleUpdate(`/OrderInfo/Get`, `/OrderInfo/GetGood`, Id, "你确定已收到货了吗", { Id: Id });

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
        async CreateOrEditForm() {

            this.$refs.editModalForm.validate(async valid => {
                if (valid) {
                    var { Success } = await this.$Post(`/OrderInfo/CreateOrEdit`, this.formData);

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
        getOrderStatusType(status) {
            const statusMap = {
                0: 'warning',  // 待支付
                1: 'primary',  // 待发货
                2: 'success',  // 待收货
                3: 'info',     // 待评价
                6: ''         // 完成
            };
            return statusMap[status] || 'info';
        },
        handleTabClick(value) {
            this.activeTab = value;
            if (value === -1) {
                this.where = { UserId: store.getters.UserId };
            } else {
                this.where = {
                    UserId: store.getters.UserId,
                    OrderStatusList: [value]
                };
            }
            this.$refs.PaginationTableId.Reload(this.where);
        },
    },
};
</script>

<style scoped>
.order-list {
    width: 100%;

}

.order-item {

    margin-bottom: 20px;
}

.product-name {
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
}

.margin-left {
    margin-left: 10px;
}

.margin-bottom {
    margin-bottom: 15px;
}

.margin-bottom-sm {
    margin-bottom: 8px;
}



.padding-y {
    padding: 15px 0;
}

.padding-y-sm {
    padding: 8px 0;
}

.padding-top {
    padding-top: 15px;
}

.text-gray {
    color: #909399;
}

.text-danger {
    color: #F56C6C;
}

.flex {
    display: flex;
}

.flex-1 {
    flex: 1;
}

.justify-between {
    justify-content: space-between;
}

.align-center {
    align-items: center;
}

.custom-tabs {
    display: flex;
    align-items: center;
    margin: 20px 0;
    border-bottom: 1px solid #eee;
    padding: 15px 20px;
    background-color: #fff;
}

.tab-item {
    position: relative;
    padding: 12px 24px;
    cursor: pointer;
    font-size: 14px;
    color: #606266;
    transition: all 0.3s;
    margin-right: 32px;
}

.tab-item:hover {
    color: var(--primary-color);
}

.tab-item.active {
    color: var(--primary-color);
    font-weight: 500;
}

.tab-line {
    position: absolute;
    bottom: -1px;
    left: 0;
    width: 100%;
    height: 2px;
    background-color: var(--primary-color);
    transition: all 0.3s;
}
</style>
<template>
    <div>
        <div class="page-container margin-top-xs card">
            <el-page-header @back="goBack()" content="订单详情">
            </el-page-header>
            <el-steps class="margin-top-lg" :active="active" finish-status="success">
                <el-step title="选择收货地址"></el-step>
                <el-step title="确认订单无误"></el-step>
                <el-step title="拍下文创品进行支付"></el-step>
                <el-step title="确认收货"></el-step>
                <el-step title="评价"></el-step>
            </el-steps>


            <div class="address-box">
                <div class="flex justify-between">
                    <div class="header-titlele">选择地址</div>
                    <div style="color:var(--primary-color);font-size: 12px;cursor: pointer;" @click="ToEditAddress()">
                        编辑地址</div>
                </div>
                <div class="address-list">
                    <div class="address-item" :class="AddressCheckedId == item.Id ? 'active' : ''"
                        v-for="(item, index) in UserAddressList" :key="index" @click="SelectAddress(item)">
                        <div class="address-name">
                            {{ item.Name }}
                        </div>
                        <div>{{ item.ProviceCityArea }},{{ item.Address }}</div>
                        <div>门牌号:{{ item.HouseNumber }}</div>
                        <div class="address-phone">
                            <span>电话:{{ item.Phone }}</span><span>门牌号:{{ item.HouseNumber }}</span>
                        </div>
                        <div>
                            性别:{{ item.Sex }}
                        </div>

                    </div>


                </div>
            </div>

            <div class="order-box">
                <div class="header-titlele">确认订单信息</div>
                <div v-for="(item, index) in OrderList" class="order-item" :key="index">
                    <div class="shop">
                        <img :src="item.ShopDto.LogoCover" class="shop-logo">
                        <div>店铺:<span>{{ item.ShopDto.Name }}</span></div>

                    </div>
                    <el-table class="margin-top-lg" :data="item.OrderDet" stripe style="width: 100%">

                        <el-table-column label="文创品图片" width="100" :align="'center'">
                            <template slot-scope="scope">
                                <img :src="scope.row.GoodDto.Cover" class="good-img" />
                            </template>
                        </el-table-column>
                        <el-table-column label="文创品名称" :align="'center'">
                            <template slot-scope="scope">
                                <span>{{ scope.row.GoodDto.Name }}</span>
                            </template>
                        </el-table-column>


                        <el-table-column label="单价" width="180" :align="'center'">
                            <template slot-scope="scope">
                                <span class="price">￥{{ scope.row.GoodDto.Price }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="name" label="数量" width="180" :align="'center'">
                            <template slot-scope="scope">
                                <el-input-number v-model="scope.row.Qty" @change="QtyChange()" :min="1"
                                    :max="scope.row.GoodDto.Stock" label="描述文字" size="mini"></el-input-number>
                            </template>
                        </el-table-column>
                        <el-table-column prop="address" label="小计" width="180" :align="'center'">
                            <template slot-scope="scope">
                                <span class="price"> {{ scope.row.Qty * scope.row.GoodDto.Price | toFixed2 }}</span>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="pickup-option">
                        <div class="pickup-header">
                            <el-checkbox v-model="item.IsSelfPickup">
                                <span class="pickup-title">到店自提</span>
                            </el-checkbox>
                        </div>
                        <div v-show="item.IsSelfPickup" class="pickup-time">
                            <i class="el-icon-time"></i>
                            <el-date-picker v-model="item.SelfPickupTime" type="datetime" placeholder="请选择预计自提时间"
                                value-format="yyyy-MM-dd HH:mm:ss" :picker-options="{
                                    disabledDate(time) {
                                        return time.getTime() < Date.now();
                                    }
                                }">
                            </el-date-picker>
                        </div>
                    </div>
                    <div class="order-remark">

                        <label class="tit">给卖家留言:</label> <el-input show-word-limit maxlength="200" type="textarea"
                            :rows="2" placeholder="选填,请输入内容" v-model="item.Remark">
                        </el-input>
                    </div>

                </div>

            </div>



            <div class="order-pay">
                <div class="header-titlele">支付方式</div>
                <div class="pay-list">
                    <div class="pay-item" @click="PaySelect('微信支付')" :class="PayType == '微信支付' ? 'active' : ''">
                        <img :src="require('../../assets/微信支付.png')">
                    </div>
                    <div class="pay-item" @click="PaySelect('支付宝支付')" :class="PayType == '支付宝支付' ? 'active' : ''">
                        <img :src="require('../../assets/支付宝支付.png')">
                    </div>
                    <div class="pay-item" @click="PaySelect('银行卡支付')" :class="PayType == '银行卡支付' ? 'active' : ''">
                        <img :src="require('../../assets/银行卡支付.png')">
                    </div>
                </div>
            </div>
            <div class="order-summary">
                <div class="header-titlele">合计</div>
                <div class="footer">
                    <div class="price"><span class="price-icon">￥</span> <span>{{ TotalMoney }}</span>
                    </div>

                    <div class="btn" @click="CreateOrder()">下 单 支 付 </div>

                </div>

            </div>

        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
export default {
    name: "ToOrder",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    data() {
        return {
            active: 1,
            AddressCheckedId: undefined,
            UserAddressList: [],
            OrderList: [],
            TotalMoney: 0,
            PayType: undefined,
            PayMoney: 0,

        };
    },
    created() {
        //得到上一个页面给的参数
        let tick = this.$route.query.tick;
        let dataJsonString = localStorage.getItem(tick);
        let dataJsonObj = JSON.parse(dataJsonString);
        console.log(dataJsonObj);
        // 为每个订单项添加自提相关属性
        dataJsonObj.forEach(item => {
            item.IsSelfPickup = false;
            item.SelfPickupTime = '';
        });
        this.OrderList = dataJsonObj;
        this.SummaryCaculate();
        this.GetUserAddressList();

    },
    methods: {
        //返回上一个页面
        goBack() {
            this.$router.go(-1);
        },

        //优惠卷按钮
        async SelectCoupon(record, state) {

            record.state = state ? "选中" : "可使用";
            this.SummaryCaculate();
            this.$forceUpdate()

        },


        //得到用户的收货地址
        async GetUserAddressList() {
            let { Data: { Items } } = await this.$Post("/UserAddress/List", { BelongUserId: this.UserId })
            this.UserAddressList = Items;
        },
        async ToEditAddress() {
            this.$router.push({
                path: '/Front/MyUserAddress',
                query: {

                }
            })
        },
        //选择地址
        async SelectAddress(item) {
            this.AddressCheckedId = item.Id;
        },
        //输入数量发生变动
        async QtyChange() {
            this.CaculateCouponUseState();
            this.SummaryCaculate();
        },
        //汇总计算
        async SummaryCaculate() {
            let orderDets = this.OrderList.map(x => x.OrderDet).flat();

            let totalMoney = 0;
            orderDets.map(x => totalMoney += x.GoodDto.Price * x.Qty)

            this.TotalMoney = parseFloat(totalMoney).toFixed(2);


            let payMoney = parseFloat(totalMoney);

            this.PayMoney = payMoney > 0 ? payMoney.toFixed(2) : "0.00";
        },
        //选中支付类型
        async PaySelect(payType) {

            this.PayType = payType;
        },
        //订单支付
        async CreateOrder() {

            if (!this.AddressCheckedId) {
                this.$message.warning('请选择收货地址');
                return;
            }
            if (!this.PayType) {
                this.$message.warning('请选择支付方式');
                return;
            }
            let selectAddress = this.UserAddressList.find(x => x.Id == this.AddressCheckedId);
            //构建后端需要的订单参数
            let body = [];
            for (let orderItem of this.OrderList) {
                let orderInfo = {
                    UserId: this.UserId,
                    ShopUserId: orderItem.ShopDto.UserId,
                    ShopId: orderItem.ShopDto.Id,
                    PayType: this.PayType,
                    TotalMoney: this.TotalMoney,
                    ReceiveAddress: selectAddress.ProviceCityArea + " " + selectAddress.Address + "[门牌号]:" + selectAddress.HouseNumber,
                    ReceivePhone: selectAddress.Phone,
                    ReceiveName: selectAddress.Name,
                    Remark: orderItem.Remark,
                    SelfPickupTime: orderItem.SelfPickupTime,
                    IsSelfPickup: orderItem.IsSelfPickup,
                    OrderDets: [],


                };

                for (let orderDetItem of orderItem.OrderDet) {
                    let orderDet = {
                        GoodId: orderDetItem.GoodDto.Id,
                        Qty: orderDetItem.Qty,
                        GoodDto: orderDetItem.GoodDto,
                        Price: orderDetItem.GoodDto.Price,
                        BuyCardId: orderDetItem.BuyCardId
                    }
                    orderInfo.OrderDets.push(orderDet);
                }
                body.push(orderInfo);

            }
            let { Success, Data: PayOrderNo } = await this.$Post("/OrderInfo/ToOrder", body);
            if (Success) {
                if (this.PayType == "支付宝支付") {
                    await this.AliPaySubmit(PayOrderNo);
                }
                else {
                    //跳转到我的订单
                    this.$message.success("下单成功!");
                    this.$router.replace({
                        path: "/Front/MyOrderList"
                    })
                }

            }




        },
        async AliPaySubmit(payOrderNo) {
            let { Data: requestContent } = await this.$Post(`/OrderInfo/AliPay`, { PayOrderNo: payOrderNo, CallBack: `http://${location.host}/#/Front/MyOrderList` });
            const payDiv = document.getElementById('payDiv');
            if (payDiv) {
                document.body.removeChild(payDiv);
            }
            const div = document.createElement('div');
            div.id = 'payDiv';
            div.innerHTML = requestContent;
            document.body.appendChild(div);
            document.getElementById('payDiv').getElementsByTagName('form')[0].submit();
        }
    }

}
</script>

<style scoped>
.address-box {
    margin-top: 20px;
}

.header-titlele {
    line-height: 25px;
    color: #333;
    font-weight: 700;
    font-size: 18px;
    margin: 5px 0px;

}

.address-list {
    display: flex;
    flex-wrap: wrap;
    margin-top: 5px;
}



.address-list .address-item {
    width: 300px;
    height: 170px;
    padding: 12px;
    margin-left: 10px;
    margin-top: 10px;
    color: #666;
    cursor: pointer;
    font-size: 12px;
    line-height: 25px;
    background-image: url(../../assets/address.png);
    background-repeat: no-repeat;
    background-size: 100% 170px;



}

.address-list .address-item:hover {
    background-image: url(../../assets/address-checked.png);

}

.address-list .active {
    background-image: url(../../assets/address-checked.png);

}

.address-list .address-item .address-name {
    font-weight: bold;
}

.address-list .address-item .address-phone {
    display: flex;
    justify-content: space-between;
}


.order-box {
    margin-top: 20px;
}

.order-box .order-item {
    margin-top: 15px;
}

.order-box .shop {

    background-color: #F3F6F8;
    border-bottom: 1px solid #F3F6F8;
    overflow: hidden;
    display: flex;
    align-items: center;
    font-size: 14px;
    color: #11192D;
    border-radius: 20px;
    padding: 10px;
    font-weight: bold;
}

.order-box .shop-logo {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 10px;
    object-fit: fill;
}



.order-box .good-img {
    width: 70px;
    height: 70px;
    object-fit: fill;
    border-radius: 10px;
}

.order-box .price {
    color: var(--primary-color);
    font-weight: 700;
    font-size: 20px;
}

.order-box .order-remark {
    display: flex;
    margin-top: 10px;

}

.order-box .order-remark .tit {
    width: 150px;
    font-size: 16px;
}

.order-summary .footer {
    display: flex;
    align-items: center;
    justify-content: space-between
}

.order-summary .footer .price {
    flex: 1;
    color: var(--primary-color);
    font-size: 30px;
    font-weight: bolder;
}

.order-summary .footer .price-icon {
    font-size: 14px;
}

.order-summary .footer .btn {
    display: inline-block;
    height: 48px;
    width: 120px;
    background-color: var(--primary-color);
    border-radius: 8px;
    font-family: PingFangSC;
    font-weight: 600;
    font-size: 16px;
    color: #fff;
    text-align: center;
    line-height: 48px;
    text-decoration: none;
    cursor: pointer;
}



.order-pay {}

.order-pay .pay-list {
    display: flex;
    flex-wrap: wrap;
}

.order-pay .pay-list .active {
    border: 1px solid var(--primary-color) !important;

}

.order-pay .pay-list .pay-item {
    margin-top: 15px;
    margin-left: 50px;
    padding: 10px;
    border-radius: 10px;
    border: 1px solid transparent;
}

.order-pay .pay-list .pay-item:hover {
    border: 1px solid var(--primary-color);

}

.order-pay .pay-list .pay-item img {
    width: 200px;
    height: 150px;
    object-fit: contain;
}

/* 自提选项样式 */
.pickup-option {
    margin: 20px 0;
    padding: 15px;
    background: #f8f9fa;
    border-radius: 8px;
    transition: all 0.3s ease;
}

.pickup-option:hover {
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.pickup-header {
    display: flex;
    align-items: center;
}

.pickup-title {
    font-size: 14px;
    margin-left: 4px;
    color: #333;
    font-weight: 500;
}

.pickup-time {
    margin-top: 15px;
    margin-left: 24px;
    display: flex;
    align-items: center;
}

.pickup-time .el-icon-time {
    color: #909399;
    margin-right: 8px;
    font-size: 16px;
}

.pickup-time .el-date-picker {
    width: 240px;
}
</style>
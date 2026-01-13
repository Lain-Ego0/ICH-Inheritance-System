<template>
    <div class="page-container ">
        <div class="card margin-top-xs">
            <el-page-header @back="goBack()" content="我的购物车">
            </el-page-header>
        </div>
        <div class="main-box margin-top-xs">

            <div class="buycard-box card">
                <h4>全部文创品</h4>
                <el-empty description="赶快去选择你想要的文创品吧" v-if="DataList.length == 0"></el-empty>
                <template v-else>
                    <div class="buycard-op">
                        <div class="allcheck"> <el-checkbox v-model="AllCheck" @change="AllCheckChange">全选</el-checkbox>
                        </div>
                        <div class="del"> <el-button type="text" size="mini" @click="BatchDeleteBuycard">删除</el-button>
                        </div>

                    </div>

                    <div class="buycard-list">

                        <div class="buycard-shop-item" v-for="(item, index) in DataList" :key="item.ShopDto.Id"
                            v-if="item.BuyCardDtos.length > 0">
                            <div class="header">
                                <img :src="item.ShopDto.LogoCover" class="logo">
                                <div>店铺:<span>{{ item.ShopDto.Name }}</span></div>

                            </div>

                            <ul class="item-content" v-for="(Buycard, BuycardIndex) in item.BuyCardDtos" :key="Buycard.Id">
                                <li class="li-checked">
                                    <el-checkbox v-model="Buycard.Checked" @change="CheckChange"></el-checkbox>
                                </li>
                                <li class="li-img"><img :src="Buycard.GoodDto.Cover" /></li>
                                <li class="li-info">
                                    <div class="title">{{ Buycard.GoodDto.Name }}</div>
                                </li>
                                <li class="li-price">
                                    <span class="price-now">￥{{ Buycard.GoodDto.Price }}</span>
                                </li>
                                <li class="li-qty">
                                    <el-input-number @change="QtyChange(Buycard)" v-model="Buycard.Qty" :min="1"
                                        :max="Buycard.GoodDto.Stock" label="描述文字" size="mini"></el-input-number>
                                </li>
                                <li calss="li-op">
                                    <el-button type="danger" size="mini"
                                        @click="DeleteBuycard(Buycard, BuycardIndex)">移除</el-button>
                                </li>
                            </ul>
                        </div>

                    </div>
                </template>
            </div>
            <div class="summary-box ">
                <div :class="ScroolTop > 150 ? 'summary-box-fixed card' : 'card'">
                    <div class="header">
                        <span class="tit-1">结算明细</span><span class="tit-selected">已选<span class="tit-selected-qty">{{
                            CheckBuyCardDtos.length
                        }}</span>件文创品
                        </span>
                    </div>
                    <!-- <el-empty description="还没有待结算的文创品" v-if="CheckBuyCardDtos.length == 0"></el-empty> -->
                    <div class="content">
                        <div class="img-list">
                            <div class="img-box" v-for="(item, index) in CheckBuyCardDtos" :key="index">
                                <img :src="item.GoodDto.Cover">
                            </div>
                        </div>
                    </div>
                    <div class="footer">
                        <div class="tit-summary">合计:</div>
                        <div class="price"><span class="price-icon">￥</span> <span>{{ TotalMoney }}</span></div>
                        <div class="btn" @click="ToOrder()">结 算 </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
    name: "buycardd",
    computed: {
        ...mapGetters(["UserId", "UserInfo"])
    },
    data() {
        return {
            DataList: [],
            AllCheck: false,
            CheckBuyCardDtos: [],//选择的购物车
            TotalMoney: 0,
            ScroolTop: 0,
        };
    },

    async created() {
        await this.GetShopSummaryListApi();
        await this.SummaryCaculate();

    },
    mounted() {
        window.addEventListener("scroll", this.ScrollCallBack)
    },
    destroyed() {

        window.removeEventListener("scroll", this.ScrollCallBack)
    },
    methods: {
        //返回上一个页面
        goBack() {
            this.$router.go(-1);
        },
        async GetShopSummaryListApi() {
            let { Data } = await this.$Post("/BuyCard/ShopSummaryList", { BelongUserId: this.UserId });
            console.log(Data);

            Data.forEach((item) => {
                item.BuyCardDtos.forEach((buycard) => {
                    buycard.Checked = false;
                })
            })

            this.DataList = Data;
        },
        //单个移除
        async DeleteBuycard(item, index) {


            let { Success } = await this.$PostDelete("/BuyCard/Delete", { Id: item.Id })
            if (Success) {
                let buyCardDtos = this.DataList.find(x => x.ShopDto.Id == item.ShopId).BuyCardDtos;
                buyCardDtos.splice(index, 1);
            }
            this.SummaryCaculate();
        },
        //修改数量
        async QtyChange(item) {

            let { Success } = await this.$Post("/BuyCard/CreateOrEdit", item);
            if (Success) {
                this.$notify({
                    title: '成功',
                    message: '修改数量成功',
                    type: 'success',
                    duration: 700
                });
            }
            this.SummaryCaculate();
        },
        //全选
        AllCheckChange() {
            this.DataList.forEach((item) => {
                item.BuyCardDtos.forEach((buycard) => {
                    buycard.Checked = this.AllCheck;
                })
            })
            this.SummaryCaculate();
        },
        //单选
        CheckChange() {
            this.SummaryCaculate();
        },
        //批量删除
        async BatchDeleteBuycard() {
            let buyCardIds = this.DataList.map(x => x.BuyCardDtos).flat().filter(x => x.Checked == true).map(x => x.Id);
            if (buyCardIds.length == 0) {
                this.$message.warning("没有选中需要删除的记录");
                return;
            }
            let { Success } = await this.$PostDelete("/BuyCard/BatchDelete", { Ids: buyCardIds })
            this.GetShopSummaryListApi();
            this.SummaryCaculate();
        },
        //汇总计算
        async SummaryCaculate() {
            let buyCardDtos = this.DataList.map(x => x.BuyCardDtos).flat().filter(x => x.Checked == true);
            this.CheckBuyCardDtos = buyCardDtos;
            let totalMoney = 0;
            this.CheckBuyCardDtos.map(x => totalMoney += x.GoodDto.Price * x.Qty)

            this.TotalMoney = parseFloat(totalMoney).toFixed(2);
        },
        //页面滚动回调监听
        async ScrollCallBack(e) {
            this.ScroolTop = window.scrollY;
        },
        //去结算
        async ToOrder() {

            let orderList = [];

            let shopIds = this.DataList.map(x => x.BuyCardDtos).flat().filter(x => x.Checked == true).map(x => x.ShopId);
            if (shopIds.length == 0) {
                this.$message.warning('请选择你要需要结算的文创品');
                return;
            }
            shopIds = Array.from(new Set(shopIds));
            for (var shopId of shopIds) {
                let data = this.DataList.find(x => x.ShopDto.Id == shopId);
                let shopDto = data.ShopDto;
                let orderDet = data.BuyCardDtos.filter(x => x.Checked == true).map(x => {
                    return {
                        GoodDto: x.GoodDto,
                        Qty: x.Qty,
                        GoodId: x.GoodId,
                        BuyCardId: x.Id,

                    }
                });
                orderList.push({
                    OrderDet: orderDet,
                    ShopDto: shopDto
                });
            }


            let tick = new Date().getTime();
            localStorage.setItem(tick, JSON.stringify(orderList));
            this.$router.push({
                path: "/Front/ToOrder",
                query: {
                    tick: tick
                }
            })
        }

    },

}
</script>

<style scoped>
.main-box {
    display: flex;

}

.main-box .buycard-box {
    flex: 1;
    margin-right: 10px;
}



.buycard-op {
    margin-top: 10px;
    display: flex;

}

.buycard-op .allcheck {
    margin-left: 25px;
}

.buycard-op .del {
    margin-left: 10px;
}

.buycard-list {
    margin-top: 15px;

}

.buycard-list .header {

    height: 50px;
    background-color: #F3F6F8;
    border-bottom: 1px solid #F3F6F8;
    overflow: hidden;
    display: flex;
    align-items: center;
    font-size: 14px;
    color: #11192D;
    font-weight: bold;


}

.buycard-list .header .logo {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin: 0px 10px;

}



.buycard-list .item-content {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    align-items: center;
    padding: 10px;
}

.buycard-list .buycard-shop-item {
    border: solid 1px #F0F3F5;
    border-radius: 8px;
    margin-bottom: 10px;
}

.buycard-list .item-content li {
    height: 100px;
}

.buycard-list .item-content .li-checked {
    width: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.buycard-list .item-content .li-img {
    width: 96px;

}

.buycard-list .item-content .li-img img {
    width: 96px;
    height: 96px;
    border-radius: 20px;

}

.buycard-list .item-content .li-info {
    width: 196px;
    padding-left: 20px;
}

.buycard-list .item-content .li-info .title {
    font-size: 14px;
    line-height: 20px;
    display: block;
    max-height: 40px;
    overflow: hidden;
    text-overflow: ellipsis;
    font-weight: 500;
    color: #11192D;
}

.buycard-list .item-content .li-price {
    width: 120px;
    padding-left: 30px;
}

.buycard-list .item-content .li-price .price-now {
    color: var(--primary-color);
    font-weight: 700;
    font-family: AlibabaSans102v1TaoBao-Bold;
    font-size: 14px;
}


.buycard-list .item-content .li-qty {
    width: 140px;
}



.summary-box {
    width: 400px;
    position: relative;
}

.summary-box-fixed {
    top: 0px;

    width: 400px;
    padding-top: 20px;
    position: fixed;
    z-index: 999;
    padding: 20px;
}

.summary-box .header {}

.summary-box .header .tit-1 {
    font-weight: bolder;
}

.summary-box .header .tit-selected {
    margin-left: 10px;
    font-size: 13px;
}

.summary-box .header .tit-selected-qty {
    font-weight: bold;
    font-size: 16px;
    padding: 0 5px;
}

.summary-box .content {}

.summary-box .content .img-list {
    display: flex;
    flex-wrap: wrap;
    min-height: 300px;

}

.summary-box .content .img-list .img-box {
    width: 72px;
    height: 72px;
    margin-top: 10px;
    margin-left: 10px;
}

.summary-box .content .img-list .img-box img {
    width: 72px;
    height: 72px;
    border-radius: 10px;
    object-fit: fill;

}

.summary-box .footer {
    margin-top: 10px;
    align-items: center;
    display: flex;
}


.summary-box .footer .tit-summary {
    font-weight: bold;
    font-size: 15px;
}

.summary-box .footer .price {
    flex: 1;
    color: var(--primary-color);
    font-size: 20px;
    font-weight: bolder;
}

.summary-box .footer .price-icon {
    font-size: 14px;
}

.summary-box .footer .btn {
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
</style>
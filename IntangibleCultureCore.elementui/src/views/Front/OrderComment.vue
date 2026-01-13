<template>
    <div>
        <div class="page-container margin-top-xs card">
            <el-page-header @back="goBack()" content="订单评价">
            </el-page-header>
            <div class="margin-top-xs">
                <el-alert title="多个文创品可选择性点评" type="success">
                </el-alert>
            </div>
            <div class="margin-top-xs">
                <div class="order-box">

                    <div class="order-item">

                        <div class="margin-top-xs" v-for="(item, index) in OrderInfo.OrderDets">
                            <el-table class="margin-top-xs" :data="[item]" stripe style="width: 100%">

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

                                <el-table-column prop="address" label="小计" width="180" :align="'center'">
                                    <template slot-scope="scope">
                                        <span class="price"> {{ scope.row.Qty * scope.row.GoodDto.Price | toFixed2
                                        }}</span>
                                    </template>
                                </el-table-column>
                            </el-table>
                            <div class="margin-top-xs">

                            </div>
                            <el-form label-width="40px">

                                <el-row>
                                    <el-col :span="24">
                                        <el-form-item label="图片" prop="ImageUrls">
                                            <UploadImages :limit="6" v-model="item.ImageUrls"></UploadImages>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="24">
                                        <el-form-item label="点评" prop="Comment">
                                            <el-input type="textarea" v-model="item.Comment" placeholder="请输入评论"
                                                :clearable="true" :rows="4"></el-input>
                                            <el-rate class="margin-top-xs" show-text v-model="item.CommentScore">
                                            </el-rate>
                                        </el-form-item></el-col>

                                </el-row>
                            </el-form>
                        </div>

                    </div>

                </div>
            </div>
            <div class="footer">
                <div class="btn" @click="OrderCommentApi()">提 交 点 评 </div>
            </div>


        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name: "OrderInfoList",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId"
        ])
    },
    data() {

        return {
            OrderInfo: {}

        };
    },
    created() {
        this.GetOrderInfoApi();
    },
    methods: {
        //查看订单详情
        async GetOrderInfoApi(item) {
            let { Data } = await this.$Post("/OrderInfo/Get", { Id: this.$route.query.OrderInfoId })

            this.OrderInfo = Data;
        },
        //返回上一个页面
        goBack() {
            this.$router.go(-1)
        },
        async OrderCommentApi() {

            let orderComments = [];
            for (let orderDet of this.OrderInfo.OrderDets) {
                if (orderDet.CommentScore && orderDet.CommentScore > 0) {
                    orderComments.push({
                        GoodId: orderDet.GoodId,
                        OrderId: orderDet.OrderId,
                        CommentScore: orderDet.CommentScore,
                        Comment: orderDet.Comment,
                        ShopId: this.OrderInfo.ShopId,
                        CommentUserId: this.OrderInfo.UserId,
                        ImageUrls: orderDet.ImageUrls,
                    })
                }
            }
            if (orderComments.length == 0) {
                this.$message.warning("请完成至少一个文创品的评分");
            }
            let { Success } = await this.$Post("/OrderComment/OrderComment", orderComments)
            if (Success) {
                this.$message.success("评价成功,即将返回上一个页面");
                this.$router.go(-1);
            }
        }





    },
};
</script>
<style scoped>
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
    color: #FF5000;
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

.footer {
    display: flex;
    flex-direction: row-reverse;

}

.footer .btn {
    display: inline-block;
    height: 48px;
    width: 120px;
    background-color: #ff5000;
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
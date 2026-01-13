<template>
    <div v-if="Detail">
        <div class="margin-top-xs">


            <div class="card">
                <el-page-header @back="goBack()" content="文创品详情">
                </el-page-header>
                <div class="shop-header margin-top-xs" @click="ToShopGood()">

                    <img :src="Detail.ShopDto.LogoCover" class="logo">
                    <div class="shop-name">{{ Detail.ShopDto.Name }}</div>
                </div>

            </div>

            <div class="good-box card">
                <div class="img-box">
                    <div class="thumbnail">
                        <div class="thumbnail-item" v-for="(item, index) in ImageList"
                            @mouseover="ThumbnailMouseOver(item.url)" :class="item.url == ActiveImgUrl ? 'active' : ''">
                            <img :src="item.url">
                        </div>
                    </div>
                    <div class="detail-img">
                        <img :src="ActiveImgUrl">
                    </div>
                </div>
                <div class="good-aside">
                    <div class="tit">
                        {{ Detail.Name }}
                    </div>
                    <div class="seller-qty">已售 {{ Detail.BuyCount }}+</div>
                    <div class="price-box">
                        <span class="price-icon">￥</span>
                        <span class="price">{{ Detail.Price }}</span>

                    </div>
                    <div class="unit">单位:{{ Detail.Unit }}</div>
                    <div class="input-qty">
                        <span class="qty-tit">数量:</span>
                        <el-input-number v-model="Qty" size="mini" :min="1" :max="Detail.Stock"></el-input-number>
                        <span class="txt">{{ Detail.Stock > 0 ? '有货' : '缺货' }}</span>
                    </div>
                    <div class="good-btn">

                        <div class="buy" @click="ToOrderBtn">立即购买</div>
                        <div class="add-buycard" @click="AddBuyCarBtn">加入购物车</div>
                        <el-button @click="CollectBtn()">{{ CollectId > 0 ? '取 消 收 藏' : '收 藏' }}</el-button>
                    </div>
                </div>
            </div>

            <div class="good-detail card">
                <el-tabs v-model="TabPage">
                    <el-tab-pane label="图文详情" name="page1">
                        <div v-html="Detail.Content" class="content"></div>
                    </el-tab-pane>
                    <el-tab-pane label="参数信息" name="page2">
                        <el-empty description="暂时没有设置文创品参数信息" v-if="GoodPropList.length == 0"></el-empty>
                        <el-descriptions class="margin-top" title="参数信息" :column="3" :size="size" border v-else>

                            <el-descriptions-item v-for="(item, index) in GoodPropList">
                                <template slot="label">
                                    {{ item.Name }}
                                </template>
                                {{ item.Value }}
                            </el-descriptions-item>

                        </el-descriptions>
                    </el-tab-pane>
                    <el-tab-pane label="用户评价" name="third">
                        <el-empty description="暂时没有评论哦" v-if="CommentList.length == 0"></el-empty>
                        <div class="comment-list" v-else>
                            <div class="comment-item" v-for="(item, index) in CommentList" :key="index">
                                <div>

                                    <img class="comment-headimg"
                                        :src="item.CommentUserDto.ImageUrls ? item.CommentUserDto.ImageUrls : require('@/assets/emptyheadimg.jpg')">
                                </div>
                                <div class="content-item">
                                    <div class="name"> {{ item.CommentUserDto.Name }}</div>
                                    <div class="comment-score">
                                        <el-rate v-model="item.CommentScore" disabled>
                                        </el-rate>
                                    </div>
                                    <div class="comment-txt">{{ item.Comment }}</div>
                                    <div class="comment-img-list" v-if="item.ImageUrlList && item.ImageUrlList.length > 0">
                                        <img :src="item.ImageUrls" v-for="(imgItem, imgIndex) in item.ImageUrlList"
                                            :key="imgIndex" />

                                    </div>
                                </div>
                            </div>
                        </div>
                    </el-tab-pane>

                </el-tabs>

            </div>

        </div>

    </div>
</template>

<script>
import { FullConvertUrlArray } from '@/utils/comm.js';
import { mapGetters } from 'vuex';
export default {
    name: "Good",
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId", "ShopId"
        ])
    },
    data() {
        return {
            Detail: null,
            ActiveImgUrl: "",
            ImageList: [],
            GoodPropList: [],
            Qty: 1,
            TabPage: "page1",
            CommentList: [],
            CollectId: 0,
        }
    },
    created() {
        this.GetGoodApi();
        this.GetGoodPropListApi();
        this.GetGoodCommentApi();
        this.GetCollectApi();

    },
    methods: {
        //返回上一个页面
        goBack() {
            this.$router.go(-1);
        },
        //得到文创品
        async GetGoodApi() {
            let { Data } = await this.$Post("/Good/Get", { Id: this.$route.query.GoodId })
            let imgs = FullConvertUrlArray(Data.ImageUrls);
            this.ActiveImgUrl = imgs[0].url;
            this.ImageList = imgs;
            this.Detail = Data;
        },
        //得到文创品的参数
        async GetGoodPropListApi() {
            let { Data: { Items } } = await this.$Post("/GoodProp/List", { GoodId: this.$route.query.GoodId });
            this.GoodPropList = Items;
        },
        //缩略图移动
        async ThumbnailMouseOver(url) {
            this.ActiveImgUrl = url;
        },
        //添加到购物车
        async AddBuyCarBtn() {
            if (this.Token) {

                let { Success } = await this.$Post("/BuyCard/CreateOrEdit", { ShopId: this.Detail.ShopId, GoodId: this.Detail.Id, BelongUserId: this.UserId, Qty: 1 });
                if (Success) {
                    this.$message.success("添加到购物车成功!");
                }
            }
            else {
                this.$message.error("请先登录再操作");
            }
        },
        //去下单
        async ToOrderBtn() {

            if (this.Token) {
                if (this.Detail.Stock == 0) {
                    this.$message.warning('文创品没有库存了');
                    return;
                }
                let data = [{
                    OrderDet: [
                        {
                            GoodDto: this.Detail,
                            Qty: this.Qty,
                            GoodId: this.Detail.Id,
                            BuyCardId: null,
                        }
                    ],
                    ShopDto: this.Detail.ShopDto,
                }];
                let tick = new Date().getTime();
                localStorage.setItem(tick, JSON.stringify(data));
                this.$router.push({
                    path: "/Front/ToOrder",
                    query: {
                        tick: tick
                    }
                })
            }
            else {
                this.$message.error("请先登录再操作");
            }
        },
        //得到文创品的评价
        async GetGoodCommentApi() {
            let { Data: { Items } } = await this.$Post("/OrderComment/List", { GoodId: this.$route.query.GoodId });
            for (let comment of Items) {
                comment.ImageUrlList = FullConvertUrlArray(comment.ImageUrls);
            }
            this.CommentList = Items;
        },
        //前往店铺文创品
        async ToShopGood() {
            this.$router.push({
                path: "/Front/ShopGood",
                query: {
                    ShopId: this.Detail.ShopId
                }
            })
        },

        //查询是否收藏过
        async GetCollectApi() {
            if (this.Token) {
                let { Data } = await this.$Post("/GoodCollect/Get", { Id: 0, GoodId: this.$route.query.GoodId, CollectUserId: this.UserId });
                this.CollectId = Data.Id;
            }
        },
        //收藏按钮
        async CollectBtn() {
            if (this.Token) {
                if (this.CollectId > 0) {
                    //取消
                    await this.$Post("/GoodCollect/Delete", { Id: this.CollectId });
                    this.CollectId = 0;
                }
                else {
                    //创建
                    let { Data } = await this.$Post("/GoodCollect/CreateOrEdit", { GoodId: this.$route.query.GoodId, CollectUserId: this.UserId });
                    this.CollectId = Data.Id;
                }

            }
            else {
                this.$message.error("请先登录再操作");
            }
        },

    }

}
</script>

<style scope>
.shop-header {
    display: flex;
    align-items: center;
}

.shop-header .logo {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin: 0 10px;
}

.shop-header .shop-name {
    font-weight: bold;
}

.good-box {
    margin-top: 20px;
    display: flex;
    flex-direction: row;

}

.good-box .img-box {
    display: flex;
    flex-direction: row;


}

.good-box .img-box .thumbnail {}

.good-box .img-box .thumbnail .thumbnail-item {
    margin-top: 5px;
}

.good-box .img-box .thumbnail img {
    width: 76px;
    height: 75px;
    border-radius: 5px;
}

.good-box .img-box .thumbnail .active {
    border: 1px solid red;
}

.good-box .img-box .detail-img {
    height: 624px;
    width: 624px;
    border-radius: 8px;
    display: flex;
    align-items: center;
    background-color: #FAFAFA;
    justify-content: center;
    margin-left: 10px;
}

.good-box .img-box .detail-img img {
    max-width: 100%;
    max-height: 100%;
    object-fit: cover;
    margin: 0 auto;
    display: block;
    border-radius: 12px;
}

.good-box .good-aside {
    padding: 0 20px;
}

.good-box .good-aside .tit {
    font-weight: bold;
}

.good-box .good-aside .seller-qty {
    margin-top: 10px;
    color: #50607a;
    font-size: 14px;
    line-height: 14px;
}

.good-box .good-aside .price-box {
    display: flex;
    align-items: flex-end;
}

.good-box .good-aside .price-icon {
    color: #B81C22;
    font-size: 16px;
    font-weight: 700;
    line-height: 16px;
    margin-bottom: 7px;
}

.good-box .good-aside .price {
    color: #B81C22;
    font-size: 24px;
    font-weight: 700;
    line-height: 36px;
    vertical-align: middle;
}

.good-box .good-aside .input-qty {
    margin-top: 10px;
}

.good-box .good-aside .input-qty .qty-tit {
    margin-right: 10px;
}

.good-box .good-aside .input-qty .txt {
    margin-left: 10px;
}



.good-box .good-aside .unit {}

.good-box .good-aside .good-btn {
    margin-top: 10px;
    display: flex;
    flex-wrap: nowrap
}

.good-box .good-aside .good-btn .buy {
    background: linear-gradient(90deg, #B81C22, #8C1519);
    vertical-align: top;
    border-top-left-radius: 8px;
    border-bottom-left-radius: 8px;
    width: 180px;
    cursor: pointer;
    height: 40px;
    line-height: 40px;
    color: white;
    font-size: 17px;
    text-align: center;
}

.good-box .good-aside .good-btn .add-buycard {
    background: linear-gradient(90deg, #D4474D, #B81C22);
    vertical-align: top;
    border-top-right-radius: 8px;
    border-bottom-right-radius: 8px;
    width: 180px;
    cursor: pointer;
    height: 40px;
    line-height: 40px;
    color: white;
    font-size: 17px;
    text-align: center;
    margin-right: 10px;
}


.good-detail {
    margin-top: 20px;
}


.good-detail .content {
    width: 600px;
    margin: 0 auto;
}

.good-detail .content img {
    max-width: 100%;
}

.good-detail .el-tabs__item.is-active {
    color: #B81C22;
}

.good-detail .el-tabs__active-bar {
    background-color: #B81C22;
}

.good-detail .el-tabs__item:hover {
    color: #B81C22;
}

.comment-list {}

.comment-list .comment-item {
    display: flex;
    flex-wrap: nowrap;
}

.comment-list .comment-item .comment-headimg {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    object-fit: cover;
}

.comment-list .comment-item .content-item {
    margin-left: 10px;
}

.comment-list .comment-item .comment-txt {
    margin-top: 10px;
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: #11192d;
    line-height: 20px;
}

.comment-list .comment-item .comment-img-list {
    display: flex;

}

.comment-list .comment-item .comment-img-list img {
    width: 80px;
    height: 80px;
    border-radius: 10px;
    margin-left: 10px;
    margin-top: 10px;
    object-fit: cover;
}
</style>
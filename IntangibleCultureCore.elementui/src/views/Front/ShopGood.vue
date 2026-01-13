<template>
    <div v-if="Shop != null">
        <div class="margin-top-xs card">
            <el-page-header @back="goBack()" content="店铺详情">
            </el-page-header>
            <div class="shop margin-top-xs">
                <img :src="Shop.LogoCover" class="shop-logo">
                <div class="shop-name">店铺:<span>{{ Shop.Name }}</span></div>

            </div>
        </div>

        <div class="margin-top-xs card">
            <div class="shop-info">
                <div class="info-item">
                    <i class="el-icon-location"></i>
                    <span class="label">店铺地址：</span>
                    <span>{{ Shop.Address }}</span>
                </div>
                <div class="info-item">
                    <i class="el-icon-phone"></i>
                    <span class="label">联系电话：</span>
                    <span>{{ Shop.Phone }}</span>
                </div>
                <div class="info-item">
                    <i class="el-icon-message"></i>
                    <span class="label">电子邮箱：</span>
                    <span>{{ Shop.Email }}</span>
                </div>
                <div class="info-item">
                    <i class="el-icon-medal"></i>
                    <span class="label">店铺保证：</span>
                    <span>{{ Shop.Ensure }}</span>
                </div>
                <div class="info-item">
                    <i class="el-icon-user"></i>
                    <span class="label">店主：</span>
                    <span>{{ Shop.CreatorAppUserDto.Name }}</span>
                </div>
                <div class="info-item">
                    <i class="el-icon-time"></i>
                    <span class="label">成立时间：</span>
                    <span>{{ Shop.CreationTime }}</span>
                </div>
            </div>
        </div>

        <div class="margin-top-xs card">
            <div class="header-title">店铺介绍</div>
            <div class="shop-content" v-html="Shop.Content"></div>
        </div>
        <div class="card margin-top-xs">
            <div class="header-title">猜你喜欢</div>
            <Pagination url="/Good/GetRecommendList" :where="{}">
                <template v-slot:content="{ data }">
                    <GoodListCard :DataList="data"></GoodListCard>
                </template>
            </Pagination>
        </div>


    </div>
</template>

<script>
import Pagination from "@/components/Pagination/PaginationBox.vue";
import { mapGetters } from 'vuex';
import GoodListCard from "./GoodListCard.vue";

export default {
    name: "ShopGood",
    components: {
        Pagination: Pagination,
        GoodListCard
    },
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId", "ShopId"
        ])
    },
    data() {
        return {
            Shop: null,
            GoodTypeList: [],
            SelectShopGoodTypeId: '',
            CollectId: 0,
            where: {},
        }
    },
    created() {
        this.where.ShopId = this.$route.query.ShopId;
        this.GetShopApi();
        this.GetGoodTypeApi();

    },
    methods: {
        //得到店铺信息
        async GetShopApi() {
            let { Data } = await this.$Post("/Shop/Get", { Id: this.$route.query.ShopId })
            this.Shop = Data;
        },


    }

}
</script>

<style scope>
.shop {

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

.shop-logo {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 10px;
    object-fit: fill;
}

.shop-name {
    flex: 1;
}

.tab {}

.tab .tit {
    font-weight: bolder;
}

.shop-info {
    padding: 20px;
}

.info-item {
    margin-bottom: 15px;
    font-size: 14px;
    color: #606266;
}

.info-item i {
    margin-right: 8px;
    color: var(--primary-color);
}

.info-item .label {
    font-weight: bold;
    margin-right: 10px;
    color: #303133;
}

.info-item:last-child {
    margin-bottom: 0;
}

.shop-content {
    padding: 20px;
    line-height: 1.8;
    font-size: 14px;
    color: #606266;
}

.header-title {
    padding: 15px 20px;
    font-size: 16px;
    font-weight: bold;
    border-bottom: 1px solid #EBEEF5;
}
</style>
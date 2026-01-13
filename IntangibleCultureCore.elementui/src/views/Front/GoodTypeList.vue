<template>
    <div v-if="Shop != null">
        <div class="margin-top-xs card">
            <el-page-header @back="goBack()" content="文创品分类">
            </el-page-header>

        </div>

        <div class="margin-top-xs card">
            <div class="tab">
                <h3>{{ GoodTypeName }}</h3>

                <Pagination url="/Good/List" ref="Pagination" :where="where">
                    <template v-slot:content="{ data }">
                        <GoodListCard :DataList="data"></GoodListCard>
                    </template>
                </Pagination>
            </div>


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
            GoodTypeName: "",
        }
    },
    created() {
        this.GoodTypeName = this.$route.query.GoodTypeName;
        this.where.GoodTypeId = this.$route.query.GoodTypeId;
        this.GetShopApi();
        this.GetGoodTypeApi();
        this.GetCollectApi();
    },
    methods: {
        //得到店铺信息
        async GetShopApi() {
            let { Data } = await this.$Post("/Shop/Get", { Id: this.$route.query.ShopId })
            this.Shop = Data;
        },

        //分类选择
        async handleClick(e) {

            this.$refs.Pagination.Reload({ ShopGoodTypeId: e.name });
        },
        //返回上一个页面
        goBack() {
            this.$router.go(-1)
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
</style>
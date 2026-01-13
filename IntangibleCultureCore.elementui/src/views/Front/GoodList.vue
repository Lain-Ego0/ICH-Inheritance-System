<template>
    <div class="page-container ">
        <div class="margin-top-xs">
            <GoodSearch @Search="SearchCallBack"></GoodSearch>
        </div>

        <div class="card margin-top-xs">
            <MarketCard></MarketCard>

        </div>

        <div class="card margin-top-xs">
            <div class="header-title margin-top-lg">官方推荐</div>
            <Pagination url="/Good/List" ref="Pagination" :where="{}" :limit="8">
                <template v-slot:content="{ data }">
                    <GoodListCard :DataList="data"></GoodListCard>
                </template>
            </Pagination>

        </div>
        <div class=" card margin-top-xs">
            <div class="header-title">猜你喜欢</div>
            <Pagination url="/Good/GetRecommendList" :where="{}" :limit="8">
                <template v-slot:content="{ data }">
                    <GoodListCard :DataList="data"></GoodListCard>
                </template>
            </Pagination>
        </div>

    </div>
</template>

<script>
import GoodSearch from "./GoodSearch.vue";
import MarketCard from "./MarketCard.vue";

import Pagination from "@/components/Pagination/PaginationBox.vue";
import GoodListCard from "./GoodListCard.vue";
export default {
    name: "Home",
    components: {
        MarketCard,
        GoodSearch,
        Pagination,
        GoodListCard
    },
    data() {
        return {
        }
    },
    created() {

    },
    methods: {
        SearchCallBack(e) {
            this.$router.push({
                path: "/Front/GoodSearchResult",
                query: {
                    KeyWord: e
                }
            })
        },
    }

}
</script>

<style scope></style>

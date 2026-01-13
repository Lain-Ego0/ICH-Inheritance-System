<template>

    <div class="good-list">
        <div class="good-item " v-for="(item, index) in GoodList" :key="item.Id" @click="ToDetail(item)">
            <img :src="item.Cover">
            <div class="tit">{{ item.Name }}</div>
            <div class="price-box">
                <span class="price-icon">￥</span>
                <span class="price">{{ item.Price }}</span>
                <span class="people">最近{{ item.BuyCount }}+付款</span>
            </div>
            <div class="shop-info">
                <span>{{ item.ShopDto.Name }}</span>
            </div>
        </div>

    </div>

</template>

<script>
import store from '@/store';
import { mapGetters } from 'vuex';

export default {
    name: "GoodListCard",
    props: {
        DataList: {
            type: [Array],
            default: () => { return [] }
        },
    },
    data() {
        return {
            GoodList: []
        }
    },
    created() {
        this.GoodList = this.DataList;
    },
    methods: {

        async ToDetail(item) {
            this.$router.push({
                path: '/Front/Good',
                query: {
                    GoodId: item.Id,

                }
            })
        }
    }

}
</script>

<style scope>
.good-list {
    display: flex;
    flex-wrap: wrap;

}



.good-list .good-item {
    width: 240px;

    margin-left: 60px;
    margin-top: 10px;
    transition: All 0.2s ease-in-out;

}

.good-list .good-item:hover {
    transform: scale(1.02);

}

.good-list .good-item img {
    width: 240px;
    height: 200px;
    border-radius: 10px;
    object-fit: cover;

}

.good-list .good-item .tit {
    -webkit-box-orient: vertical;
    word-wrap: break-word;
    -webkit-line-clamp: 2;
    color: #11192d;
    display: -webkit-box;
    font-family: PingFangSC-Medium;
    font-size: 16px;
    font-weight: 500;
    height: 48px;
    line-height: 24px;
    overflow: hidden;
    text-overflow: ellipsis;
    vertical-align: middle;
    width: 100%;
    word-break: break-all;
}

.good-list .good-item .price-box {
    display: flex;
    align-items: flex-end;
}

.good-list .good-item .price-icon {
    color: var(--primary-color);
    font-size: 16px;
    font-weight: 700;
    line-height: 16px;
    margin-bottom: 7px;
}

.good-list .good-item .price {
    color: var(--primary-color);

    font-size: 24px;
    font-weight: 700;
    line-height: 36px;
    vertical-align: middle;
}

.good-list .good-item .people {
    color: #50607a;
    font-size: 14px;
    line-height: 14px;
    margin-bottom: 8px;
    white-space: nowrap;
    margin-left: 5px;
}

.good-list .good-item .shop-info span {
    color: #50607a;
    cursor: pointer;
    font-size: 14px;
    font-weight: 400;
    letter-spacing: 0;
    line-height: 16px;
    max-width: 180px;
    overflow: hidden;
    text-decoration: none !important;
    text-overflow: ellipsis;
    white-space: nowrap;
}
</style>
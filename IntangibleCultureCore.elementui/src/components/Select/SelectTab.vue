
<template>
    <div>
        <div class="filter-label" style="margin-top: 20px">{{ title }}:</div>
        <div class="filter-content">
            <span :class="['filter-item', value === '' ? 'active' : '']" @click="Select('')">全部</span>
            <span v-for="item in options" :key="item.value" :class="['filter-item', active === item.value ? 'active' : '']"
                @click="Select(item.value)">{{ item.name
                }}</span>
        </div>
    </div>
</template>

<script>

export default {

    props: {
        value: {
            type: [Number, String],
            default: ''
        },
        url: {
            type: String, //默认的请求路径
            default: "",
        },
        title: {
            type: String, //类型名称
            default: "",
        },

        BindName: { //默认的列属性
            type: String, //默认的请求路径
            default: "",
        },
        BindValue: {
            type: String, //默认的请求路径
            default: "",
        },
        where: {},


    },
    watch: {

    },
    computed: {

    },
    data() {
        return {
            active: undefined,
            loading: false, //是否正在获取数据中
            options: [],
            where_: {},
            pagination: {
                Limit: 9999,
                Page: 1,
                Total: 0,
                PageSizes: [5, 10, 20, 50, 100],
            },


        }
    },
    created() {

        this.where_ = this.$props.where;
        this.FetchDataList();

    },
    methods: {

        InitData() {
            this.pagination.Limit = 100;
            this.pagination.Page = 1;
            this.pagination.Total = 0;
            this.where_ = {};
        },
        async FetchDataList() {
            this.loading = true;
            let {
                Data: {
                    Items,
                    TotalCount
                }
            } = await this.$Post(this.$props.url, {
                Limit: this.pagination.Limit,
                Size: this.pagination.Limit,
                Page: this.pagination.Page,
                ...this.where_
            });
            this.loading = false;
            let dataList = [];
            Items.forEach((item, index) => {
                dataList.push({
                    name: item[`${this.BindName}`],
                    value: item[`${this.BindValue}`] ?? item[`${this.BindValue}`].toString(),
                });
            });
            this.options = dataList;
            this.pagination.Total = TotalCount;
        },
        Select(value) {
            this.active = value;
            this.$emit('input', value);
            this.$emit('Select', value);

        },

    }
}

</script>

<style scoped>
.filters-container {

    padding: 20px;
    border-radius: 8px;

    margin: 20px 0;
}

.filter-section {
    margin: 15px 0;
    padding: 0;
    background: transparent;
    box-shadow: none;
}

.filter-section:first-child {
    margin-top: 0;
}

.filter-section:last-child {
    margin-bottom: 0;
}

.filter-label {
    color: #606266;
    font-size: 14px;
    font-weight: bold;
    margin-bottom: 10px;
}

.filter-content {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
}

.filter-item {
    padding: 6px 15px;
    border-radius: 4px;
    font-size: 14px;
    cursor: pointer;
    transition: all 0.3s;
    color: #606266;
    background: #f5f7fa;
}

.filter-item:hover {
    color: var(--primary-color);
    background: #ecf5ff;
}

.filter-item.active {
    color: #fff;
    background: var(--primary-color);
}
</style>


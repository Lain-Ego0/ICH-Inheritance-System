<template>
    <div>

        <div id="aiEditor" style="height: 550px;  margin: 20px"></div>
    </div>
</template>

<script>
import { PostUpload } from "@/api/http";
import { AiEditor } from "aieditor";
import "aieditor/dist/style.css";
export default {
    name: "AIRichText",
    props: {
        value: {
            type: String,
            default: ''
        },
        height: {
            type: String,
            default: '300px'
        },
    },


    data() {
        let that = this;
        return {
            uploadUrl: process.env.VUE_APP_BASE_API + "/File/BatchUpload",
            fileList: []
        };
    },
    created() {

        this.$nextTick(() => {
            new AiEditor({
                element: "#aiEditor",
                placeholder: "点击输入内容...",
                theme: "light",
                content: this.value,
                contentIsMarkdown: false,
                contentRetention: false,
                contentRetentionKey: 'ai-editor-content',
                draggable: true,
                pasteAsText: false,
                image: {
                    uploadUrl: this.uploadUrl,
                    uploader: async (file, uploadUrl, headers, formName) => {
                        const formData = new FormData();
                        formData.append('file', file);

                        let { Success, Data } = await PostUpload(uploadUrl, formData);

                        return {
                            "errorCode": 0,
                            "data": {
                                "src": Data[0].Url,
                                "alt": Data[0].FileName,
                                "align": "center",
                                "width": "100%",
                                "height": "auto",
                                "class": "image-class",
                                "loading": true,
                                "data-src": Data[0].Url
                            }
                        };
                    },
                },
                onChange: (aiEditor) => {

                    this.$emit('input', aiEditor.getHtml())
                },

                ai: {
                    models: {
                        spark: {
                            appId: "2e9be894",
                            apiKey: "69b882dc87fb511d0b32198636617d57",
                            apiSecret: "NWE5OTJmZmM1MjAxYjViYTQ4OTdjMzg0",
                            version: "v1.1"
                        }
                    }
                }
            })
        })
    },

    methods: {


    }
}
</script>

<style></style>
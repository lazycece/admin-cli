<!--
  @author lazy c
  @email lazycece@gmail.com
  2018/5/25
-->
<template>
	<div>
    <el-upload
      class="image-uploader" :data="returnParamData" action="" :httpRequest="handleUpload" drag :multiple="false" :show-file-list="false"
      :before-upload="beforeToUpload"
      :on-remove="removeImage">
      <img class="imageView" v-if="returnImageUrl" :src="returnImageUrl">
      <i v-else class="el-icon-plus image-uploader-icon"></i>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2M</div>
    </el-upload>
	</div>
</template>

<script>
  import { uploadFile } from '@/api/components/components'

  export default {
    name: 'uploadImageSingle',
    props: {
      imageUrl: {
        type: String,
        default: ''
      },
      dir: {
        type: String,
        required: true
      },
      attach: {
        type: String,
        default: ''
      }
    },
    computed: {
      returnImageUrl() {
        return this.imageUrl
      },
      returnParamData() {
        return {
          dir: this.dir,
          attach: this.attach
        }
      }
    },
    methods: {
      beforeToUpload(file) {
        let requireType = false
        if (file.type === 'image/jpeg' || file.type === 'image/png') {
          requireType = true
        }
        const size2M = file.size / 1024 / 1024 < 2
        if (!requireType) {
          this.$message.error('只支持JPG和PNG格式')
        }
        if (!size2M) {
          this.$message.error('图片限制大小为2MB')
        }
        return requireType && size2M
      },
      transferUrl(imageUrl) {
        this.$emit('on-upload-image', imageUrl)
      },
      removeImage() {
        this.transferUrl('')
      },
      handleUpload(uploadData) {
        const formData = new FormData()
        formData.append('file', uploadData.file)
        formData.append('dir', this.dir)
        formData.append('attach', this.attach)
        uploadFile(formData).then(response => {
          this.$message.success('文件上传成功')
          this.transferUrl(response.data)
        }).catch(_ => {})
      }
    }
  }
</script>

<style>
  .image-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .image-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .image-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .imageView {
     width: auto;
     height: auto;
     max-width: 100%;
     max-height: 100%;
   }
</style>


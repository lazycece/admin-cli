<!--
  @author lazy c
  @email lazycece@gmail.com
  2018/5/25
-->
<template>
  <div>
    <input id="file-upload-input" type="file" class="c-hide" @change="handleFileChange">
    <div id="drop" @drop="handleDrop" @dragover="handleDragover" @dragenter="handleDragover">
      Drop file here or
      <el-button style="margin-left:16px;" size="mini" type="primary" @click="handleUpload">Upload</el-button>
    </div>
  </div>
</template>

<script>
  import { uploadFile } from '@/api/components/components'
  export default {
    name: 'uploadFile',
    props: {
      dir: {
        type: String,
        required: true
      },
      attach: {
        type: String,
        default: ''
      }
    },
    data() {
      return {
        loading: false,
        fileUrl: ''
      }
    },
    methods: {
      transferUrl() {
        this.$emit('on-upload-file', this.fileUrl)
      },
      handleDrop(e) {
        e.stopPropagation()
        e.preventDefault()
        const files = e.dataTransfer.files
        if (files.length !== 1) {
          this.$message.warning('一次性仅支持上传一个文件')
          return
        }
        this.doUpload(files[0])
        e.stopPropagation()
        e.preventDefault()
      },
      handleDragover(e) {
        e.stopPropagation()
        e.preventDefault()
        e.dataTransfer.dropEffect = 'copy'
      },
      handleUpload() {
        document.getElementById('file-upload-input').click()
      },
      handleFileChange(e) {
        const files = e.target.files
        this.doUpload(files[0])
      },
      doUpload(file) {
        if (file.size > 1024 * 1024 * 2) {
          this.$message.warning('文件超过2M')
          return
        }
        const formData = new FormData()
        formData.append('file', file)
        formData.append('dir', this.dir)
        formData.append('attach', this.attach)
        uploadFile(formData).then(response => {
          this.$message.success('文件上传成功')
          this.fileUrl = response.data
          this.transferUrl()
        }).catch(_ => {})
      }
    }
  }
</script>

<style scoped>
#file-upload-input{
  display: none;
  z-index: -9999;
}
#drop{
  border: 2px dashed #bbb;
  width: 600px;
  height: 160px;
  line-height: 160px;
  margin: 0 auto;
  font-size: 24px;
  border-radius: 5px;
  text-align: center;
  color: #bbb;
  position: relative;
}
</style>

/**
 * @author cc
 * @email lazycece@gmail.com
 */
export function successNotify(object) {
  object.$notify({
    title: '成功',
    message: '操作成功',
    type: 'success',
    position: 'top-left',
    offset: 100,
    duration: 2000
  })
}

export function successMessage(object, message) {
  object.$message({
    type: 'success',
    message: message
  })
}

export function warningMessage(object, message) {
  object.$message({
    type: 'warning',
    message: message
  })
}

export function infoMessage(object, message) {
  object.$message({
    type: 'info',
    message: message
  })
}

export function errorMessage(object, message) {
  object.$message({
    type: 'error',
    message: message
  })
}

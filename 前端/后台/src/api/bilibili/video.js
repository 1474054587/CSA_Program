import request from '@/utils/request'
export default {
  getVideoInfo(bvid) {
    return request({
      url: `/eduservice/bili/getVideoInfo/${bvid}`,
      method: 'get'
    })
  },
  getVideoSrc(bvid, cid) {
    return request({
      url: `/eduservice/bili/getVideoSrc/${bvid}/${cid}`,
      method: 'get'
    })
  },
  getVideoListByUP(mid, page, limit) {
    return request({
      url: `/eduservice/bili/getVideoListByUP/${mid}/${page}/${limit}`,
      method: 'get'
    })
  },
}

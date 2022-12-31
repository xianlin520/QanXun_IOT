new Vue({
    el: '#app',
    mounted() {
        var logsList = [];
        function getUrlParams(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]);
            return null;
        }

        // 获取path参数
        var path = getUrlParams('path');
        // 向后端发送请求
        var xhr = new XMLHttpRequest();
        xhr.open('GET', '/token/getlogdata/' + path, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                var data = xhr.responseText;
                // 按行分隔
                data = data.split('\n');
                for (let log of data) {
                    if (log.length === 0) break;
                    let date = log.substring(0, log.indexOf(' ['))
                    let level = log.match(/\[(.*?)\]/g)[0];
                    let className = log.substring(log.indexOf('] ') + 2, log.indexOf(': '))
                    let message = log.substring(log.indexOf(': ') + 2)
                    logsList.unshift({"date":date, "level":level, "className":className, "message":message})

                    // console.log('时间:', date);
                    // console.log('等级:', level);
                    // console.log('类名:', className);
                    // console.log('消息:', message);
                }
            }
        };

        xhr.send(null);
        this.tableData = logsList
    },
    methods: {
        tableRowClassName({row, rowIndex}) {
            if (rowIndex === 1) {
                return 'warning-row';
            } else if (rowIndex === 3) {
                return 'success-row';
            }
            return '';
        }
    },
    data() {
        return {
            tableData:[]
        }
    }})
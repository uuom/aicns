function createChart(width,dataStr){

    if(dataStr == null){
        return;
    }
    var data = JSON.parse(dataStr);

    var chartDiv = $('#main');
    //设置宽高
    chartDiv.css("width", width);
    chartDiv.css("height", width*0.75);
    //基于准备好的dom，初始化echarts图表
    myChart = echarts.init(document.getElementById("main"));
    var option = {
        title : {
            text: '所有告警-告警分布图',
            x:'center'
        },
        tooltip : {
            trigger: 'item'
        },
       dataRange: {
            min: 0,
            max: 20,
            x: 'left',
            y: 'bottom',
            text:['高','低'],           // 文本，默认为数值文本
            calculable : true
        },
       series : [
            {
                name: '告警个数：',
                type: 'map',
                mapType: 'china',
                roam: false,
                itemStyle:{
                    normal:{label:{show:true},color:"#FFFFFF",borderColor:"#e9e9e9"},
                    emphasis:{label:{show:true}}
                },
                data:data
            }
        ]
    };
    // 为echarts对象加载数据
    myChart.clear();
    myChart.setOption(option);
}
$(document).ready(function () {

  Date.daysBetween = function( difference_ms ) {
    //Get 1 day in milliseconds
    var one_day=1000*60*60*24;

    //take out milliseconds
    var milliseconds = Math.floor(difference_ms % 1000)
    difference_ms = difference_ms/1000;
    var seconds = Math.floor(difference_ms % 60);
    difference_ms = difference_ms/60;
    var minutes = Math.floor(difference_ms % 60);
    difference_ms = difference_ms/60;
    var hours = Math.floor(difference_ms % 24);
    var days = Math.floor(difference_ms/24);

    if(days < 10) days = "0"+days
    if(hours < 10) hours = "0"+hours
    if(minutes < 10) minutes = "0"+minutes
    if(seconds < 10) seconds = "0"+seconds
    if(milliseconds < 10) milliseconds = "00"+milliseconds
    if(milliseconds < 100) milliseconds = "0"+milliseconds
    return hours + ':' + minutes + ':' + seconds + '.' + milliseconds;
  }

  var response  = {},
      clock     = 0,
      interval;

  function start() {
    $.post("/api", function (data) {
      response = data
      interval = setInterval(update, 10)
    })
  }

  function stop() {
    $.post("/api/"+response.id, function (data) {
      clearInterval(interval)
      render()
    })
  }

  function update() {
    clock = Date.daysBetween(new Date() - Date.parse(response.startTime))
    render()
  }

  function render() {
    $("#timer0").html(clock)
  }

  $("#startButton").click(start)
  $("#stopButton").click(stop)
})
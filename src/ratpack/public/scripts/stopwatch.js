/*
http://stackoverflow.com/questions/20318822/how-to-create-a-stopwatch-using-javascript
This link got me started.  TODO: refactor
*/

var Stopwatch = function(elem, options) {
  var localTimer  = createLocalTimer(),
      remoteTimer = createRemoteTimer(),
      startButton = createButton("start", start),
      stopButton  = createButton("stop", stop),
      localClock,
      remoteClock,
      startStopwatchRecord = {},
      stopwatchRecord = {};

  options = options || {}
  options.delay = options.delay || 1

  elem.appendChild(localTimer)
  elem.appendChild(remoteTimer)
  elem.appendChild(startButton)
  elem.appendChild(stopButton)

  function createLocalTimer() {
    e = document.createElement("span")
    e.id = "localTimer"

    startTimeLabel           = document.createElement("span")
    e.appendChild(startTimeLabel)
    startTimeLabel.id        = "localTimerStartTimeLabel"
    startTimeLabel.innerHTML = "Local start time: "

    startTimeValue           = document.createElement("span")
    e.appendChild(startTimeValue)
    startTimeValue.id        = "localTimerStartTimeValue"

    endTimeLabel             = document.createElement("span")
    e.appendChild(endTimeLabel)
    endTimeLabel.id          = "localTimerEndTimeLabel"
    endTimeLabel.innerHTML   = "Local end time: "

    endTimeValue             = document.createElement("span")
    e.appendChild(endTimeValue)
    endTimeValue.id          = "localTimerEndTimeValue"

    durationLabel            = document.createElement("span")
    e.appendChild(durationLabel)
    durationLabel.id         = "localTimerDurationLabel"
    durationLabel.innerHTML  = "Local duration: "

    durationValue            = document.createElement("span")
    e.appendChild(durationValue)
    durationValue.id         = "localTimerDurationValue"
    return e
  }

  function createRemoteTimer() {
    e = document.createElement("span")
    e.id = "remoteTimer"

    startTimeLabel           = document.createElement("span")
    e.appendChild(startTimeLabel)
    startTimeLabel.id        = "remoteTimerStartTimeLabel"
    startTimeLabel.innerHTML = "Remote start time: "

    startTimeValue           = document.createElement("span")
    e.appendChild(startTimeValue)
    startTimeValue.id        = "remoteTimerStartTimeValue"

    endTimeLabel             = document.createElement("span")
    e.appendChild(endTimeLabel)
    endTimeLabel.id          = "remoteTimerEndTimeLabel"
    endTimeLabel.innerHTML   = "Remote end time: "

    endTimeValue             = document.createElement("span")
    e.appendChild(endTimeValue)
    endTimeValue.id          = "remoteTimerEndTimeValue"

    durationLabel            = document.createElement("span")
    e.appendChild(durationLabel)
    durationLabel.id         = "remoteTimerDurationLabel"
    durationLabel.innerHTML  = "Remote duration: "

    durationValue            = document.createElement("span")
    e.appendChild(durationValue)
    durationValue.id         = "remoteTimerDurationValue"
    return e
  }

  function createButton(action, handler) {
    var a = document.createElement("a")
    a.href = "#" + action
    a.innerHTML = action
    a.addEventListener("click", function(event) {
      handler()
      event.preventDefault()
    })
    return a
  }

  function start() {
    var now = new Date()
    $("#localTimerStartTimeValue").html(now.toISOString())
    $("#localTimerEndTimeValue").html("")
    $("#localTimerDurationValue").html("")

    $.post("/api", function(data) {
      stopwatchRecord = data
      startStopwatchRecord = data
      $("#remoteTimerStartTimeValue").html(stopwatchRecord.startTime)
      $("#remoteTimerEndTimeValue").html("")
      $("#remoteTimerDurationValue").html("")
    })
  }

  function stop() {
    var now = new Date()
    $("#localTimerEndTimeValue").html(now.toISOString())
    var localDurationMillis = now - Date.parse($("#localTimerStartTimeValue").text())
    $("#localTimerDurationValue").html(localDurationMillis/1000)

    $.post("/api/"+startStopwatchRecord.id, function(data) {
      stopwatchRecord = data
      $("#remoteTimerStartTimeValue").html(stopwatchRecord.startTime)
      $("#remoteTimerEndTimeValue").html(stopwatchRecord.endTime)
      $("#remoteTimerDurationValue").html(stopwatchRecord.duration/1000)
    })
  }

  this.start  = start
  this.stop   = stop
}

$(".stopwatch").each(function() { new Stopwatch(this) })
STOPWATCH
-----------------------------

I started this because it was easy.  I kept working on it because it was easy.  It's a stopwatch api.

######API
There are very few calls.  It's a very small service, a micro-service?

* POST /stopwatch
  - 'starts' a stopwatch.  It just stores a record with the current time.
* POST /stopwatch/:id
  - 'stops' a stopwatch.  It stores a record with the start time of the record with the provided id, the current time as the end time, and the difference as the duration in milliseconds.
* GET /stopwatch/:id
  - Returns the start time, end time, and duration of the record with the provided id.

The implementation is immutable, so the id returned when 'stopping' a stopwatch will be different from the id of the stopwatch you told it to 'stop'. This limits the capabilities pretty drastically and may need reconsideration for functionality but it enables very simple persistence and scalability.

There's no delete call. Because of this and immutability the API doesn't *need* any authentication. This is however open to abuse since a caller could crush the server by creating tons of records so I'm considering how it should be limited.

######Precision
Due to it's nature I'm not prioritizing precision. Any kind of guarantee would be in the seconds range at best.

######Justification
It's pretty difficult to justify but maybe it can find a use.  It's a good exercise at least.

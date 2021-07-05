Apache Tomcat is one of the more popular server implementations for Java web applications and runs in a Java Virtual Machine (JVM).

Connectors:
These are elements that enable Tomcat to receive requests from clients. One instance of a connector listens for requests on a specific
TCP port number on a server.

Each incoming request is processed by a thread in Tomcat. So, the number of requests that tomcat can handle at any given time in parallel
is determined by the the number of threads that are configured.

*maxThreads* attribute of a connector defines the maximum number of simultaneous threads that can be executing for a connector.
The number of simultaneous threads executing depends on the hardware and the number of CPUs (or cores) it has. The better the hardware and higher
the number of processors, the greater the concurrency that Tomcat will need to support.

(If the maxThreads attribute is set too low, requests will need to wait until a thread becomes available to process the request. This can increase response times seen by users.)

Another important connector setting is the *acceptCount*.
This is the max length of the accept queue where requests are placed while waiting for a processing thread. When the accept queue is full,
additional incoming requests will be refused. i.e., once all the threads are busy serving requests, and if there are more incoming requests,
then those requests are put in a queue having a size acceptCount.

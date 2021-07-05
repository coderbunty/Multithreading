Apache Tomcat is one of the more popular server implementations for Java web applications and runs in a Java Virtual Machine (JVM).

Connectors:
These are elements that enable Tomcat to receive requests from clients. One instance of a connector listens for requests on a specific
TCP port number on a server.

Each incoming request is processed by a thread in Tomcat. So, the number of requests that tomcat can handle at any given time in parallel
is determined by the the number of threads that are configured.

(http://tomcat.apache.org/tomcat-8.5-doc/config/http.html#Standard_Implementation)

maxThreads:
The maximum number of request processing threads to be created by this Connector, which therefore determines the maximum number of simultaneous requests that can be handled. If not specified, this attribute is set to 200. If an executor is associated with this connector, this attribute is ignored as the connector will execute tasks using the executor rather than an internal thread pool. Note that if an executor is configured any value set for this attribute will be recorded correctly but it will be reported (e.g. via JMX) as -1 to make clear that it is not used.

The number of simultaneous threads executing depends on the hardware and the number of CPUs (or cores) it has. The better the hardware and higher
the number of processors, the greater the concurrency that Tomcat will need to support.

(If the maxThreads attribute is set too low, requests will need to wait until a thread becomes available to process the request. This can increase response times seen by users.)

acceptCount:
The maximum length of the operating system provided queue for incoming connection requests when maxConnections has been reached. The operating system may ignore this setting and use a different size for the queue. When this queue is full, the operating system may actively refuse additional connections or those connections may time out. The default value is 100.

maxConnections:
The maximum number of connections that the server will accept and process at any given time. When this number has been reached, the server will accept, but not process, one further connection. This additional connection be blocked until the number of connections being processed falls below maxConnections at which point the server will start accepting and processing new connections again. Note that once the limit has been reached, the operating system may still accept connections based on the acceptCount setting. The default value varies by connector type. For NIO and NIO2 the default is 10000. For APR/native, the default is 8192.

minSpareThreads:
The minimum number of threads always kept running. This includes both active and idle threads. If not specified, the default of 10 is used. If an executor is associated with this connector, this attribute is ignored as the connector will execute tasks using the executor rather than an internal thread pool. Note that if an executor is configured any value set for this attribute will be recorded correctly but it will be reported (e.g. via JMX) as -1 to make clear that it is not used.

maxKeepAliveRequests:
The maximum number of HTTP requests which can be pipelined until the connection is closed by the server. Setting this attribute to 1 will disable HTTP/1.0 keep-alive, as well as HTTP/1.1 keep-alive and pipelining. Setting this to -1 will allow an unlimited amount of pipelined or keep-alive HTTP requests. If not specified, this attribute is set to 100.

connectionTimeout:
The number of milliseconds this Connector will wait, after accepting a connection, for the request URI line to be presented. Use a value of -1 to indicate no (i.e. infinite) timeout. The default value is 60000 (i.e. 60 seconds) but note that the standard server.xml that ships with Tomcat sets this to 20000 (i.e. 20 seconds). Unless disableUploadTimeout is set to false, this timeout will also be used when reading the request body (if any).

acceptorThreadCount:
The number of threads to be used to accept connections. Increase this value on a multi CPU machine, although you would never really need more than 2. Also, with a lot of non keep alive connections, you might want to increase this value as well. Default value is 1.

executor:
A reference to the name in an Executor element. If this attribute is set, and the named executor exists, the connector will use the executor, and all the other thread attributes will be ignored. Note that if a shared executor is not specified for a connector then the connector will use a private, internal executor to provide the thread pool.

Tomcat configuration used in a example application:
MAX_KEEP_ALIVE_REQUESTS defaults to 100
MAX_CONNECTIONS defaults to 200
ACCEPT_COUNT defaults to 100
CONNECTION_TIMEOUT defaults to 20000
KEEP_ALIVE_TIMEOUT defaults to 20000
MAX_HTTP_HEADER_SIZE defaults to 8192(8k)
MAX_THREADS defaults to 50
MIN_SPARE_THREADS defaults to 25
WM_ACCEPTOR_THREAD_COUNT defaults to 1

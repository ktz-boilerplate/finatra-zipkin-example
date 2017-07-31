# finatra_zipkin_example
This is finatra + http + thrift + zipkin Example.

### There is 5 sub-project.
- example-core: core sub project which used by http-server, thrift-server 
- thrift-idl is project for thrift file. It made Scala case class by scrooge.
- thrift-server is project for finatra thrift server. Called by Http Server
- thrift-server is project for finatra http server. frontend call Thrift Server
- thrift-client is wrapping project used by http server.

### how to call zipkin by 
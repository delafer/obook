interceptors folder

Usage notes
To use the same instance of HttpInterceptors for the entire app, import the HttpClientModule only in your AppModule, and add the interceptors to the root application injector . If you import HttpClientModule multiple times across different modules (for example, in lazy loading modules), each import creates a new copy of the HttpClientModule, which overwrites the interceptors provided in the root module.

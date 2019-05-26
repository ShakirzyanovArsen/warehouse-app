FROM golang:1.11 AS builder

ADD https://github.com/golang/dep/releases/download/v0.4.1/dep-linux-amd64 /usr/bin/dep
RUN chmod +x /usr/bin/dep
WORKDIR $GOPATH/src/warehouse-app
COPY . ./
RUN dep ensure --vendor-only
RUN CGO_ENABLED=0 GOOS=linux go build -a -installsuffix nocgo -o /app .

FROM alpine:3.7
COPY --from=builder /app ./
RUN mkdir /config
VOLUME ["/config/config.json"]
EXPOSE 80
WORKDIR /
ENTRYPOINT ["./app"]



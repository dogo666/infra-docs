FROM golang:alpine
RUN mkdir /app
COPY . /app
WORKDIR /app
RUN chmod +x /app/rpgo
CMD ["./rpgo"]

# FROM golang:latest as builder
# RUN apt-get update
# ENV GO111MODULE=on \
#     CGO_ENABLED=0 \
#     GOOS=linux \
#     GOARCH=amd64
# WORKDIR /go/scr
# COPY go.mod .
# RUN go mod download
# COPY . .
# RUN go build main.go

# FROM scratch
# COPY --from=builder /go/src .
# ENTRYPOINT ["./main"]
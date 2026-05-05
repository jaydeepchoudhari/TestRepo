FROM nexus-docker-cne.ci.duke-energy.app/duke/platform-engineering/eks-base:1.0.0 AS builder
 
ARG PRODUCT_NAME
ARG APPLICATION_NAME
ARG APPLICATION_BUILD_ENV
ENV PRODUCT_NAME="$PRODUCT_NAME" APPLICATION_NAME="$APPLICATION_NAME" APPLICATION_BUILD_ENV="$APPLICATION_BUILD_ENV"
 
WORKDIR /src
COPY . /src
RUN /usr/local/share/build-scripts/angular.sh
 
FROM nexus-docker-cne.ci.duke-energy.app/duke/platform-engineering/static-runtime:1.0.0
WORKDIR /src
COPY --from=builder /src/dist /src
 
USER app
EXPOSE 8080
ENTRYPOINT []
CMD ["/usr/local/share/run-scripts/static.sh"]

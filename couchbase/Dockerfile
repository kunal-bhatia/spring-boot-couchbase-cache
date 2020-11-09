FROM couchbase:enterprise-6.6.0

COPY configure.sh /opt/couchbase
RUN chmod +x /opt/couchbase/configure.sh

HEALTHCHECK --interval=1m30s --timeout=3s --retries=20 CMD curl --fail http://localhost:8091/index.html || exit 1

CMD ["/opt/couchbase/configure.sh"]
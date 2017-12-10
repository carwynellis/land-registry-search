# land-registry-search

Query land registry sold price data using elasticsearch.

The intent is to ingest the land registry price paid data so that it can be
queried in various ways.

## TODO

* CSV parse and ingest into elasticsearch
 * refine implementation and add integration tests
 * use bulk api for ingest - one index call per row is slow

* Query API

* Some sort of frontend?

* Multi-mod project to separate ingest from query service

* Logging

* Config

* Try using elastic4s


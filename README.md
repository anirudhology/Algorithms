# Algorithms

Implementations of common data structures, algorithms, system design concepts, and many more.

## System Design

### Bloom Filter

A Bloom filter is a probabilistic data structure used for membership testing, designed to efficiently determine whether
an element is a member of a set.

It works by hashing elements and storing the resulting hash values in a bit array. When
querying the Bloom filter for membership, the hash values of the queried element are computed, and the corresponding
bits in the array are checked. If all the bits are set to 1, the element is likely to be in the set, but false positives
are possible.

Bloom filters are commonly used in various applications where fast set-membership testing is required,
such as network routers for caching, spell checkers, database systems, and web browsers for URL filtering, enabling
quick elimination of non-existent elements without the need for expensive disk or network access.

[Implementation](src/main/java/com/anirudhology/systemdesign/bloomfilter/BloomFilter.java)

### Consistent Hashing

Consistent hashing is a technique used in distributed systems to efficiently distribute data across multiple nodes while
minimizing the impact of node additions or removals.

It works by mapping each piece of data and each node to points on a hash ring, typically using a hash function.
This mapping ensures that each piece of data is assigned to a specific node while also providing load balancing and
fault tolerance.

One of the primary use cases of consistent hashing is in distributed caching systems, such as distributed key-value
stores or content delivery networks (CDNs). By using consistent hashing, these systems can distribute the data evenly
across a dynamic set of nodes, allowing them to scale and handle high loads effectively while maintaining data locality
and minimizing the need for data migration when nodes are added or removed.
Additionally, consistent hashing is used in load balancing algorithms and distributed databases to achieve similar
benefits of scalability and fault tolerance.

[Implementation](src/main/java/com/anirudhology/systemdesign/hashing/ConsistentHashing.java)

### Rate Limiting

Rate limiting is a technique used to control the rate of requests or operations in a system, preventing it from being
overwhelmed by too many requests at once. Rate limiting is typically applied to APIs, web servers, and other network
services to ensure fair usage and prevent abuse or denial-of-service attacks.

There are several types of rate limiting techniques:

1. Fixed Window: In this approach, a fixed time window is defined, and a maximum number of requests allowed within that
   window is enforced. Requests that exceed the limit are
   rejected. [Implementation](src/main/java/com/anirudhology/systemdesign/ratelimiting/FixedWindowCounter.java)
2. Sliding Window: Similar to fixed window, but instead of resetting the count at the end of each window, it
   continuously
   tracks the number of requests within a sliding time window. This allows for smoother handling of bursty
   traffic. [Implementation](src/main/java/com/anirudhology/systemdesign/ratelimiting/SlidingWindowCounter.java)
3. Token Bucket: This algorithm maintains a bucket of tokens, where each token represents the permission to perform a
   single request. Tokens are added to the bucket at a fixed rate, and requests can only be processed if tokens are
   available. Unused tokens are
   discarded. [Implementation](src/main/java/com/anirudhology/systemdesign/ratelimiting/TokenBucket.java)
4. Leaky Bucket: In this approach, a leaky bucket holds a fixed number of tokens. Tokens are added to the bucket at a
   fixed
   rate, but if the bucket is full, excess tokens "leak" out. Requests are processed if there are tokens available in
   the
   bucket. [Implementation](src/main/java/com/anirudhology/systemdesign/ratelimiting/LeakyBucket.java)
5. Sliding Window Log: This technique maintains a log of request timestamps within a sliding time window. Requests are
   accepted or rejected based on the number of timestamps in the log within the time
   window. [Implementation](src/main/java/com/anirudhology/systemdesign/ratelimiting/SlidingWindowLog.java)
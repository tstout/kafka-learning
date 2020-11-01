# Kafaka Toolkit

A tool for experimenting with Kafka producers and consumers.

# Produce Messages
clj -A:ktk -p <number of messages to publish>

# Consume Messages
clj -A:ktk -c

# Consume messages using a specific consumer group id
clj -A:ktk -c -g foo.bar

# Show options
clj -A:ktk -h
FROM php:7.4-cli

RUN apt-get update &&\
    apt-get install -y libzip-dev zlibc zlib1g libmemcached-dev zlib1g-dev &&\
    docker-php-ext-configure zip &&\
    docker-php-ext-install zip

RUN pecl install memcached &&\
    docker-php-ext-enable memcached
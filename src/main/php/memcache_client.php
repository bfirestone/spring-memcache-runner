<?php

echo "starting client test\n";

$host = "localhost";
$port = 11211;
$key = "HelloWorld";
$expiry = 300;

$shortopts = "";
$longopts  = array(
    "host::",
    "port::",
    "key::",
    "value::",
    "expiry::"
);

$options = getopt($shortopts, $longopts);

if (isset($options['host'])) {
    echo "setting host to {$options['host']}\n";
    $host = $options['host'];
}

if (isset($options['port'])) {
    echo "setting port to {$options['port']}\n";
    $port = $options['port'];
}

if (isset($options['key'])) {
    echo "setting key to {$options['key']}\n";
    $key = $options['key'];
}

if (isset($options['expiry'])) {
    echo "setting expiry to {$options['expiry']}\n";
    $expiry = $options['expiry'];
}

echo "connecting to memcache host={$host} port={$port}\n";
$memcache_client = new Memcached();
$memcache_client->addServer($host, $port);

if (isset($options['value'])) {
    $status = $memcache_client->set($key, $options['value'], intval($expiry));
    echo "setting key=[{$key}] to value=[{$options['value']}] expire=[{$expiry}] status=[{$status}]\n";

}

$v = $memcache_client->get($key);
echo "key=[{$key}] value=[{$v}]\n";
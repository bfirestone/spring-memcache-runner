import argparse

from pymemcache.client import base

parser = argparse.ArgumentParser()

parser.add_argument("--host",
                    action='store',
                    help="echo the string you use here",
                    type=str, default="localhost")

parser.add_argument("--port",
                    action='store',
                    help="echo the string you use here",
                    type=int, default=11211)

parser.add_argument("--key",
                    action='store',
                    help="echo the string you use here",
                    type=str, default="MyTest")

parser.add_argument("--value",
                    action='store',
                    help="echo the string you use here",
                    type=str)

parser.add_argument("--expiry",
                    action='store',
                    help="echo the string you use here",
                    type=int, default=300)

args = parser.parse_args()

print("Connecting to memcache host={} port={}".format(args.host, args.port))
client = base.Client((args.host, args.port))

if args.value:
    print("setting key={} value={}".format(args.key, args.value))
    client.set(args.key, args.value, expire=args.expiry)

print("getting value for key={}".format(args.key))
value = client.get(args.key)

print("key=[{}] value=[{}]".format(args.key, args.value))

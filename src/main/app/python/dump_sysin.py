#!/usr/bin/python

import sys
import tempfile

while True:
    data = sys.stdin.readline()
    if data:
        f = tempfile.NamedTemporaryFile(dir='/tmp/mule/in', delete=False)
        f.write(data)
        f.close()
        print 'Wrote %s to %s' % (data, f.name)

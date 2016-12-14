#!/usr/bin/env python
#coding:utf-8
import sys
import time
import datetime
from toughradius.common import utils
from toughradius.common import dispatch,logger
from toughradius import models
from toughradius.common.dbutils import make_db
from toughradius.tasks.task_base import TaseBasic
from toughradius.manage.settings import  ONLINE_STATCACHE_KEY
from twisted.internet import reactor

class OnlineStatTask(TaseBasic):

    __name__ = 'online-stat'

    def first_delay(self):
        return 5

    def process(self, *args, **kwargs):
        self.logtimes()
        with make_db(self.db) as db:
            try:
                dstr = "%s 00:00:00" % datetime.datetime.now().strftime("%Y-%m-%d")
                startstat = datetime.datetime.strptime(dstr, "%Y-%m-%d %H:%M:%S")
                online_count = db.query(models.TrOnline.id).count()
                olstat = self.cache.get(ONLINE_STATCACHE_KEY) or []
                for ol in olstat:
                    stat_time = datetime.datetime.fromtimestamp(ol[0]/1000.0)
                    if stat_time < startstat:
                        olstat.remove(ol)
                olstat.append( (int(time.time()*1000),online_count) )
                self.cache.update(ONLINE_STATCACHE_KEY,olstat)
                logger.info("online stat task done")
            except Exception as err:
                logger.error('online_stat_job err,%s'%(str(err)))
        
        return 120.0

taskcls = OnlineStatTask


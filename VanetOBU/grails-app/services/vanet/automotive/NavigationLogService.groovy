package vanet.automotive

import grails.transaction.Transactional

@Transactional
class NavigationLogService {

    def readNewNavigationLog(){
		return NavigationLog.find("from NavigationLog nl order by id desc")
	}
}

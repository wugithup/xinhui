package psdi.webclient.components;

import psdi.webclient.system.controller.AppInstance;
import psdi.webclient.system.controller.ComponentInstance;
import psdi.webclient.system.controller.PageInstance;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.runtime.WebClientRuntime;
import psdi.webclient.system.session.WebClientSession;

import java.util.Iterator;
import java.util.Stack;

/**
 *
 * @ClassName: PageComponent
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lull lull@shuto.cn
 * @date 2017年7月19日 下午11:19:40
 *
 */
public class PageComponent extends ComponentInstance {
	public int renderDialog(PageInstance dialog, boolean isRerendering) {
		WebClientSession wcs = getWebClientSession();

		if (!isRerendering) {
			try {
				dialog.handleEvent(new WebClientEvent("render", dialog.getId(), wcs.getCurrentEvent(), wcs));
				if (!getWebClientSession().getDesignmode()) {
					dialog.cleanup();
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}

		if (isRerendering) {
			dialog.put("rerendering", "true");
		}
		wcs.handleEvent(new WebClientEvent("render", dialog.getId(), wcs.getCurrentEvent(), wcs));
		if (isRerendering) {
			dialog.remove("rerendering");
		}

		return 1;
	}

	public int renderDialogs(boolean isRerendering) {
		WebClientSession wcs = getWebClientSession();
		AppInstance app = wcs.getCurrentApp();

		Iterator dialogs = app.getPoppedDialogs().iterator();
		while (dialogs.hasNext()) {
			PageInstance dialog = (PageInstance) dialogs.next();
			try {
				if (!isRerendering) {
					dialog.handleEvent(new WebClientEvent("render", dialog.getId(), wcs.getCurrentEvent(), wcs));
				}
				String currentPageId = getWebClientSession().getCurrentApp().getCurrentPageId();
				if ((!getWebClientSession().getDesignmode()) && (!currentPageId.equals("msgbox"))
						&& (dialog.getRenderId() != currentPageId))
					dialog.cleanup();
				dialogs.remove();
			} catch (Throwable t) {
				t.printStackTrace();
			}

		}

		app.showwfinfo();

		Stack allPages = app.getPageStack();
		if ((allPages != null) && (allPages.size() > 1)) {
			for (int pageIndex = 1; pageIndex < allPages.size(); pageIndex++) {
				PageInstance dilaog = (PageInstance) allPages.elementAt(pageIndex);
				if (isRerendering) {
					dilaog.put("rerendering", "true");
				}
				wcs.handleEvent(new WebClientEvent("render", dilaog.getId(), wcs.getCurrentEvent(), wcs));
				if (!isRerendering)
					continue;
				dilaog.remove("rerendering");
			}

		}

		return 1;
	}

	public int setDebugPosition() {
		String pos = getWebClientSession().getCurrentEvent().getValueString();

		String left = "0";
		String top = "0";
		if (!WebClientRuntime.isNull(pos)) {
			int index = pos.indexOf(":");
			if (index > 0) {
				left = pos.substring(0, index);
				top = pos.substring(index + 1);
			} else {
				System.err.println("setDebugPosition() called with an invalid position string: " + pos);
			}
		} else {
			System.err.println("setDebugPosition() called with a null position string.");
		}
		getWebClientSession().setDebugDock(left, top);
		return 1;
	}
}
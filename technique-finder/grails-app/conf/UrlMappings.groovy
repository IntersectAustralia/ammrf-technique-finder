class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
	      constraints {
			 controller matches: /\w+/
			 action matches: /\w+/
			 id matches: /\d+/
		  }
	  }
      "/"(controller:'homePage')
      "/admin"(controller:'admin')
	  "400"(controller:'homePage')
	  "404"(view:'/error')
	  "500"(view:'/error')
	}
}

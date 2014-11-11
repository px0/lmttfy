$(function()
  {

	  function typeOut(target, string, cb, attr)
	  {
		  console.log(cb);
		  $(target).typed(
			  {
			  strings: [string],
			  showCursor: false,
			  attr: attr,
			  callback: (typeof cb == "undefined" ? function() {} : cb),
			  typeSpeed: 0
		  });
	  }

	  function typeTitle(cb)
	  {
		  typeOut("#Title", "TTitleTitleitle", cb, "value");
	  }

	  function typeDescription(cb)
	  {
		  typeOut("#Description", "This is my content!!!", cb);
	  }

	  function showPassiveAgressiveDiv(cb)
	  {
		  $("#passiveAgressive").fadeIn("slow");
		  cb();
	  }

	  function clickOnButton(cb)
	  {
		  button = $(".button");
		  $("#fakeMouse").animate(
			  {
			  top: (button.position().top + 15),
			  left: (button.position().left + 10)
		  }, 1500, 'swing', function()
		  {
			  //button.click();
			  $(".button").css("background-color", "#3498DB");
			  cb();
		  });
	  };

	  function goToTicketPage(cb)
	  {
		  var url = window.location.href;
		  var ticketurl = "http://genome.klick.com/tasks/ticket/create"
		  querystring = url.substring(url.indexOf("?"), url.length);
		  prefilledTicket = ticketurl + querystring;

		  console.log(prefilledTicket);
		  cb();

		  //setTimeout(function(){window.location.href = prefilledTicket},2000);
	  }

	  function simpleChain(chain)
	  {
		  if (chain.length == 1)
			  {
				  return chain[0]
			  };
			  var fn = chain.shift();
			  var sc = simpleChain(chain);
			  return function()
			  {
				  fn(sc)
			  };
	  }

	  simpleChain([typeTitle,
				  typeDescription,
				  clickOnButton,
				  showPassiveAgressiveDiv,
				  goToTicketPage
	  ])();

  });


<head>
<title>Reset password request</title>
<meta name='layout' content='main' />
</head>

<body>
	<div class="nav">
      <span class="menuButton">Reset password form</span>
    </div> <!-- /nav -->
    
	<div id='login'>
		<div class='inner'>
			<g:if test='${flash.message}'>
			<div class='login_message'>${flash.message}</div>
			</g:if>
                        <div>Please provide your username (email):</div>
			<form action='reset' method='POST' id='resetForm' class='cssform'>
				<p>
					<label for='username'>Login ID</label>
					<input type='text' class='text_' name='username' id='username' value='' />
				</p>
				<p>
					<input type='submit' value='Reset' />
				</p>
			</form>
		</div>
	</div>
<script type='text/javascript'>
<!--
(function(){
	document.forms['resetForm'].elements['username'].focus();
})();
// -->
</script>
</body>

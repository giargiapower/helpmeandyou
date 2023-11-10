<template>
	<footer class="footer mt-auto">
		<div class="container">
			<div class="row">
				<div class="col-lg-6 col-md-6">
					<h4 class="footer-title"><span class="footer-title-italic"> HelpMe&You</span></h4>
				</div>
				<div class="col-lg-3">
					<h3 class="footer-title">Autori</h3>
					<ul class="author-list">
						<li><p>Anna Fontana</p></li>
						<li><p>Fernando Serrano</p></li>
						<li><p>Gianni Molinari</p></li>
					</ul>
				</div>
			</div>
		</div>

		<div class="footer-bottom">
			<div class="container">
				<p class="pull-left copyright">Copyright © 2023. All rights reserved.</p>
			</div>
		</div>
	</footer>
</template>

<script>
	export default {
		name: "PageFooter"
	}
</script>

<style scoped>
	.footer {
		background: linear-gradient(to right, #575757, #1a1a1a);
		color: white;
		padding: 50px 0;
	}

	.footer-title-italic {
		font-style: italic;
		font-size: 50px;
	}

	.footer-title {
		font-size: 30px;
		color: #76ecb7;
		margin-bottom: 1em;
	}

	.author-list {
		list-style: none;
		padding: 0;
		font-size: 14px;
	}

	.author-list li {
		margin-bottom: 0.5em;
	}

	.author-list li:hover {
		text-decoration:none;
		font-weight: bold;
	}

	.footer-bottom {
		padding: 20px 10px;
		text-align: center;
		margin-top: 2em;
		border-top: 1px solid #DDDDDD;
	}

	.copyright {
		font-size: 12px;
		color: #aaa;
	}
</style>

from flask import Flask

def create_app():
    app = Flask(__name__,instance_relative_config=False)
    app.secret_key = 'secretito'
    with app.app_context():
        from routes.route_familia import router_familia
        from routes.route_generador import router_generador
        from routes.route_transaccion import router_transaccion
        app.register_blueprint(router_familia)
        app.register_blueprint(router_generador)
        app.register_blueprint(router_transaccion)
    return app
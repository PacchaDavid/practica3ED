from .route_familia import *
from .route_generador import G_URL

router_transaccion = Blueprint('router_transaccion',__name__)

T_URL = f'{BASE_URL}/transaccion'

# TABLE TRANSACCIÓN ========================================================================

@router_transaccion.route('/transaccion/table')
def table_transaccion():
    response = requests.get(f'{T_URL}/all')
    attributes = requests.get(f'{T_URL}/attributes').json()['data']
    transacciones = response.json()['data']
    i = 1
    for transaccion in transacciones:
        transaccion['numero'] = i
        headers = {'Content-Type':'application/json'}
        familia = requests.get(f'{F_URL}/get/{transaccion['familiaId']}',headers=headers).json()['data']
        transaccion['apellidosFamilia'] = familia['apellidosRepresentantes']
        generador = requests.get(f'{G_URL}/get/{transaccion['generadorId']}',headers=headers).json()['data']
        transaccion['generadorModelo'] = generador['modelo']
        i += 1
    return render_template('transaccion/table_transaccion.html',transacciones=transacciones,attributes=attributes)

# BÚSQUEDA Y ORDENACIÓN ========================================================================

@router_transaccion.route('/transaccion/search/<attribute>/<value>')
def transaccion_search(attribute,value):
    response = requests.get(f'{T_URL}/search/{attribute}/{value}')
    attributes = requests.get(f'{T_URL}/attributes').json()['data']
    transacciones = response.json()['data']
    for i in range(0,len(transacciones)): 
        transacciones[i]['numero'] = i+1
    return render_template('transaccion/table_transaccion.html',transacciones=transacciones,attributes=attributes)

@router_transaccion.route('/transaccion/sort/<attribute>/<orden>/<typesort>')
def transaccion_sort(attribute,orden,typesort):
    response = requests.get(f'{T_URL}/sort/{attribute}/{orden}/{typesort}')
    attributes = requests.get(f'{T_URL}/attributes').json()['data']
    transacciones = response.json()['data']
    for i in range(0,len(transacciones)): 
        transacciones[i]['numero'] = i+1
    return render_template('transaccion/table_transaccion.html',transacciones=transacciones,attributes=attributes)

# TRANSACCION SAVE ========================================================================

@router_transaccion.route('/transaccion/save')
def save_transaccion():
    response = requests.get(f'{F_URL}/all')
    familias = response.json()['data']
    response = requests.get(f'{G_URL}/all')
    generadores = response.json()['data']
    usosGenerador =  requests.get(f'{T_URL}/enumerations').json()['data']
    context={
        'familias' : familias,
        'generadores' : generadores,
        'usosGenerador' : usosGenerador
    }
    return render_template('/transaccion/save_transaccion.html',context=context)
    
@router_transaccion.route('/transaccion/save/send',methods=['POST'])
def save_transaccion_send():
    data = request.form.to_dict()
    headers = {'Content-Type':'application/json'}
    response = requests.post(f'{T_URL}/save/',json=data,headers=headers)
    msg = response.json()
    key = ['info','info'] if response.status_code == 201 else ['data','error']
    flash(f'{msg['status']}: {msg[key[0]]}',category=key[1])
    return redirect('/transaccion/table')

# TRANSACCION UPDATE ========================================================================

@router_transaccion.route('/transaccion/update/<int:id>')
def update_transaccion(id):
    response = requests.get(f'{T_URL}/get/{id}')
    transaccion = response.json()['data']
    response = requests.get(f'{G_URL}/all')
    generadores = response.json()['data']
    response = requests.get(f'{F_URL}/all')
    familias = response.json()['data']
    enums = requests.get(f'{T_URL}/enumerations').json()['data']
    context = {'familias': familias, 'generadores': generadores, 'enums' : enums}
    return render_template('transaccion/update_transaccion.html',transaccion=transaccion,context=context)

@router_transaccion.route('/transaccion/update/send',methods=['POST'])
def update_transaccion_send():
    json = request.form.to_dict()
    headers = {'Content-Type':'application/json'}
    response = requests.post(f'{T_URL}/update',json=json,headers=headers)
    msg = response.json()
    key = ['info','info'] if response.status_code == 200 else ['data','error']
    flash(f'{msg['status']}: {msg[key[0]]}',category=key[1])
    return redirect('/transaccion/table')

# TRANSACCION DELETE ========================================================================

@router_transaccion.route('/transaccion/delete/<int:id>')
def delete_transaccion(id):
    response = requests.get(f'{T_URL}/get/{id}')
    transaccion = response.json()['data']
    return render_template('transaccion/delete_transaccion.html',transaccion=transaccion)

@router_transaccion.route('/transaccion/delete/send',methods=['POST'])
def delete_transaccion_send():
    response = requests.delete(f'{T_URL}/delete/{request.form['id']}')
    msg = response.json()
    key = ['info','info'] if response.status_code == 200 else ['data','error']
    flash(f'{msg['status']}: {msg[key[0]]}',category=key[1])
    return redirect('/transaccion/table')

